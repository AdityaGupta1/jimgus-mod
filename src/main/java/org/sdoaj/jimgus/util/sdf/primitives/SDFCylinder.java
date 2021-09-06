package org.sdoaj.jimgus.util.sdf.primitives;

// not technically correct because the SDF is just 0 above and below, but it is convenient
public class SDFCylinder extends SDFLine {
    public SDFCylinder(float radius, float height) {
        this(radius, height, true);
    }

    public SDFCylinder(float radius, float height, boolean centered) {
        super(0, centered ? -height / 2 : 0, 0, 0, centered ? height / 2 : height, 0);
        this.radius(radius);
        this.disableCaps();
    }
}
