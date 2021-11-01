package org.sdoaj.jimgus.util.mesh;

import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.math.Vec3f;

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
}