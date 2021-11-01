package org.sdoaj.jimgus.util.mesh;

import org.sdoaj.jimgus.Jimgus;

public class MeshFileHelper {
    private static final String prefix = "assets/" + Jimgus.MODID + "/objs/";

    public static String getFilePath(String name) {
        return prefix + name;
    }
}
