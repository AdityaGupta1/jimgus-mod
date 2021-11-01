package org.sdoaj.jimgus.util;

import org.sdoaj.jimgus.util.math.Vec3f;

public class Plane {
    private final Vec3f point;
    private final Vec3f normal;

    public Plane(Vec3f point, Vec3f normal) {
        this.point = point;
        this.normal = normal;
    }

    public float distanceToPoint(Vec3f pos) {
        return this.normal.dot(pos.subtract(this.point));
    }

    public boolean isPointInFront(Vec3f pos) {
        return isPointInFront(pos, 0);
    }

    public boolean isPointInFront(Vec3f pos, float padding) {
        return distanceToPoint(pos) > padding;
    }
}
