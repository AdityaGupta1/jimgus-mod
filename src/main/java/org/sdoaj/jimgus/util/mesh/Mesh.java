package org.sdoaj.jimgus.util.mesh;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.Plane;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Face {
    int[] vertexIds;
    int[] textureIds;
}

public class Mesh {
    public final List<Vec3f> vertices = new ArrayList<>();
    public final List<float[]> textureCoordinates = new ArrayList<>();
    public final List<Face> triangles = new ArrayList<>();

    private Texture texture = null;

    private float triangleSidePadding = 0f;

    private Mesh() {}

    public static Mesh fromOBJ(String name) {
        return Mesh.fromOBJ(name, null);
    }

    public static Mesh fromOBJ(String name, String textureName) {
        try {
            return fromOBJInternal(name, textureName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("file is broke: " + name);
        }
    }

    private static Mesh fromOBJInternal(String name, String textureName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Mesh.class.getClassLoader()
                .getResourceAsStream(MeshFileHelper.getFilePath(name)), StandardCharsets.UTF_8));

        Mesh mesh = new Mesh();

        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(" ");

            if (line.startsWith("v ")) {
                mesh.vertices.add(new Vec3f(Float.parseFloat(split[1]),
                        Float.parseFloat(split[2]),
                        Float.parseFloat(split[3])));
            } else if (line.startsWith("vt ")) {
                mesh.textureCoordinates.add(new float[]{Float.parseFloat(split[1]),
                        Float.parseFloat(split[2])});
            } else if (line.startsWith("f ")) {
                List<Integer> ids = new ArrayList<>();
                List<Integer> vts = new ArrayList<>();
                for (int i = 1; i < split.length; i++) {
                    String[] vertexSplit = split[i].split("/");
                    ids.add(Integer.parseInt(vertexSplit[0]) - 1);

                    String vtString = vertexSplit[1];
                    if (!vtString.equals("")) {
                        vts.add(Integer.parseInt(vtString) - 1);
                    }
                }

                for (int i = 0; i < ids.size() - 2; i++) {
                    Face face = new Face();
                    face.vertexIds = new int[]{ids.get(0), ids.get(i + 1), ids.get(i + 2)};
                    face.textureIds = new int[]{vts.get(0), vts.get(i + 1), vts.get(i + 2)};
                    mesh.triangles.add(face);
                }
            }

            line = reader.readLine();
        }

        reader.close();

        if (textureName != null) {
            if (mesh.textureCoordinates.isEmpty()) {
                throw new IllegalArgumentException("the mesh has no texture coordinate information you absolute buffoon");
            }

            mesh.texture = new Texture(textureName);
        }

        return mesh;
    }

    public Mesh setTriangleSidePadding(float padding) {
        this.triangleSidePadding = padding;
        return this;
    }

    // TODO add options to rotate/scale when porting to C++

    public void fill(StructureWorld world, BlockPos origin, float thickness, Block block) {
        this.fill(world, origin, thickness, block.defaultBlockState());
    }

    // takes only StructureWorld since things that require meshes are likely huge structures
    public void fill(StructureWorld world, BlockPos origin, float thickness, BlockState state) {
        for (Face face : this.triangles) {
            this.fillTriangle(world, face, origin, thickness, state, null);
        }
    }

    // takes only StructureWorld since things that require meshes are likely huge structures
    public void fill(StructureWorld world, BlockPos origin, float thickness, Function<Color, BlockState> stateFunction) {
        if (this.texture == null) {
            throw new IllegalArgumentException("need a texture");
        }

        for (Face face : this.triangles) {
            this.fillTriangle(world, face, origin, thickness, null, stateFunction);
        }
    }

    private void fillTriangle(StructureWorld world, Face face, BlockPos origin, float thickness, BlockState state, Function<Color, BlockState> stateFunction) {
        float thicknessRadius = thickness / 2;

        Vec3f pos1 = vertices.get(face.vertexIds[0]).offset(origin);
        Vec3f pos2 = vertices.get(face.vertexIds[1]).offset(origin);
        Vec3f pos3 = vertices.get(face.vertexIds[2]).offset(origin);

        Vec3f min = Vec3f.min(pos1, Vec3f.min(pos2, pos3));
        Vec3f max = Vec3f.max(pos1, Vec3f.max(pos2, pos3));
        BlockPos minPos = min.toBlockPos(f -> (int) Math.floor(f - thicknessRadius));
        BlockPos maxPos = max.toBlockPos(f -> (int) Math.ceil(f + thicknessRadius));

        Vec3f edge1 = pos2.subtract(pos1);
        Vec3f edge2 = pos3.subtract(pos2);
        Vec3f edge3 = pos1.subtract(pos3);
        Vec3f normal = edge1.cross(edge2).normalize();

        if (edge1.equalsEpsilon(Vec3f.ZERO) || edge2.equalsEpsilon(Vec3f.ZERO) || edge3.equalsEpsilon(Vec3f.ZERO)) {
            return;
        }

        Plane[] planes = new Plane[5];
        planes[0] = new Plane(pos1.add(normal.multiply(thicknessRadius)), normal);
        planes[1] = new Plane(pos1.subtract(normal.multiply(thicknessRadius)), normal.multiply(-1));
        planes[2] = new Plane(pos1, edge1.cross(normal));
        planes[3] = new Plane(pos2, edge2.cross(normal));
        planes[4] = new Plane(pos3, edge3.cross(normal));

        Plane centerPlane = new Plane(pos1, normal);

        int paddingInt = (int) Math.ceil(this.triangleSidePadding);
        for (int x = minPos.getX() - paddingInt; x < maxPos.getX() + paddingInt; x++) {
            for (int y = minPos.getY() - paddingInt; y < maxPos.getY() + paddingInt; y++) {
                for (int z = minPos.getZ() - paddingInt; z < maxPos.getZ() + paddingInt; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Vec3f posF = new Vec3f(pos);

                    boolean isInPrism = true;
                    for (int i = 0; i < 5; i++) {
                        Plane plane = planes[i];
                        if (plane.isPointInFront(posF, i <= 1 ? 0f : this.triangleSidePadding)) {
                            isInPrism = false;
                            break;
                        }
                    }

                    if (!isInPrism) {
                        continue;
                    }

                    BlockState stateToPlace;
                    if (stateFunction == null) {
                        stateToPlace = state;
                    } else {
                        Vec3f planePos = centerPlane.project(posF);
                        float area = edge1.cross(edge2).length();

                        float[] contributions = new float[3];
                        contributions[0] = edge2.cross(planePos.subtract(pos3)).length();
                        contributions[1] = edge3.cross(planePos.subtract(pos1)).length();
                        contributions[2] = edge1.cross(planePos.subtract(pos2)).length();

                        float totalContribution = 0f;
                        float areaSign = Math.signum(area);
                        for (float contribution : contributions) {
                            if (Math.signum(contribution) == areaSign) {
                                totalContribution += contribution;
                            }
                        }

                        for (int i = 0; i < 3; i++) {
                            contributions[i] /= totalContribution;
                        }

                        float[] coords = new float[2];
                        for (int i = 0; i < 2; i++) {
                            coords[i] = contributions[0] * this.textureCoordinates.get(face.textureIds[0])[i]
                                    + contributions[1] * this.textureCoordinates.get(face.textureIds[1])[i]
                                    + contributions[2] * this.textureCoordinates.get(face.textureIds[2])[i];
                        }

                        stateToPlace = stateFunction.apply(this.texture.getColor(coords[0], coords[1]));
                    }

                    world.setBlock(pos, stateToPlace);
                }
            }
        }
    }
}
