package org.sdoaj.jimgus.util.mesh;

public class Meshes {
    public static final Mesh CUBE = Mesh.fromOBJ("cube.obj");
    public static final Mesh PEPSIMAN = Mesh.fromOBJ("pepsiman/pepsiman.obj",
            "pepsiman/textures/pepsiman_color.png")
            .setTriangleSidePadding(0.5f);
    public static final Mesh PEPSI_CAN = Mesh.fromOBJ("pepsi_can/pepsi_can.obj",
            "pepsi_can/textures/pepsi_can_color.jpg")
            .setTriangleSidePadding(1.0f);
}
