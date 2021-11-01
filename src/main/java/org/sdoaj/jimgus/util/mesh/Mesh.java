package org.sdoaj.jimgus.util.mesh;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.Plane;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Mesh {
    public final List<Vec3f> vertices = new ArrayList<>();
    public final List<int[]> triangles = new ArrayList<>();

    private Mesh() {}

    public static Mesh fromOBJ(String name) {
        try {
            return fromOBJInternal(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("file is broke: " + name);
        }
    }

    private static Mesh fromOBJInternal(String name) throws IOException {
        String path = "assets/" + Jimgus.MODID + "/objs/" + name + ".obj";
        BufferedReader reader = new BufferedReader(new InputStreamReader(Mesh.class.getClassLoader()
                .getResourceAsStream(path), StandardCharsets.UTF_8));

        Mesh mesh = new Mesh();

        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("v ")) {
                String[] coordStrings = line.split(" ");
                Vec3f vertex = new Vec3f(Float.parseFloat(coordStrings[1]),
                        Float.parseFloat(coordStrings[2]),
                        Float.parseFloat(coordStrings[3]));
                mesh.vertices.add(vertex);
            } else if (line.startsWith("f ")) {
                String[] idStrings = line.split(" ");
                List<Integer> ids = new ArrayList<>();
                for (int i = 1; i < idStrings.length; i++) {
                    ids.add(Integer.parseInt(idStrings[i].split("/")[0]) - 1);
                }

                for (int i = 0; i < ids.size() - 2; i++) {
                    mesh.triangles.add(new int[]{ids.get(0), ids.get(i + 1), ids.get(i + 2)});
                }
            }

            line = reader.readLine();
        }

        reader.close();
        return mesh;
    }

    public void fill(StructureWorld world, BlockPos start, float thickness, Block block) {
        this.fill(world, start, thickness, block.defaultBlockState());
    }

    // takes only StructureWorld since things that require meshes are likely huge structures
    public void fill(StructureWorld world, BlockPos start, float thickness, BlockState state) {
        for (int[] face : this.triangles) {
            Mesh.fillTriangle(world, this.vertices.get(face[0]).offset(start),
                    this.vertices.get(face[1]).offset(start),
                    this.vertices.get(face[2]).offset(start),
                    thickness, state);
        }
    }

    private static final float triangleSidePadding = 0.5f;

    private static void fillTriangle(StructureWorld world, Vec3f pos1, Vec3f pos2, Vec3f pos3, float thickness, BlockState state) {
        float thicknessRadius = thickness / 2;

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

        for (int x = minPos.getX(); x < maxPos.getX(); x++) {
            for (int y = minPos.getY(); y < maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z < maxPos.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Vec3f posF = new Vec3f(pos);

                    boolean isInPrism = true;
                    for (int i = 0; i < 5; i++) {
                        Plane plane = planes[i];
                        if (plane.isPointInFront(posF, i <= 1 ? 0f : triangleSidePadding)) {
                            isInPrism = false;
                            break;
                        }
                    }

                    if (isInPrism) {
                        world.setBlock(pos, state);
                    }
                }
            }
        }
    }
}
