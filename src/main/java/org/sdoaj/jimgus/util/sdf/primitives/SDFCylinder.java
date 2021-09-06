package org.sdoaj.jimgus.util.sdf.primitives;

public class SDFCylinder extends SDFLine {
    public SDFCylinder(float height) {
        this(height, false);
    }

    public SDFCylinder(float height, boolean centered) {
        super(0, centered ? -height / 2 : 0, 0, 0, centered ? height / 2 : height, 0);
        this.disableCaps();
    }
}
