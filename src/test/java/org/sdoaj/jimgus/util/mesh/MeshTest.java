package org.sdoaj.jimgus.util.mesh;

import org.junit.jupiter.api.Test;
import org.sdoaj.jimgus.util.math.Vec3f;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MeshTest {
    @Test
    public void testFromOBJVertices() throws IOException {
        Mesh cube = Mesh.fromOBJ("cube.obj");

        assertTrue(cube.vertices.get(0).equalsEpsilon(new Vec3f(1, 1, -1)));
        assertTrue(cube.vertices.get(7).equalsEpsilon(new Vec3f(-1, -1, 1)));
    }

    @Test
    public void testFromOBJFaces() throws IOException {
        Mesh cube = Mesh.fromOBJ("cube.obj");

        assertArrayEquals(new int[]{0, 4, 6}, cube.triangles.get(0).vertexIds);
        assertArrayEquals(new int[]{0, 6, 2}, cube.triangles.get(1).vertexIds);
    }
}