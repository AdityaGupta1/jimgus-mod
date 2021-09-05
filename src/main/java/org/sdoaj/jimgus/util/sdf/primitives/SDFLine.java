package org.sdoaj.jimgus.util.sdf.primitives;

import net.minecraft.world.phys.Vec3;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFLine extends SDFPrimitive {
    private final float x1, y1, z1;
    private final float r1, r2;
    private boolean roundCaps = true;

    private final Vec3f pos1;
    private final Vec3f pos2;
    private final Vec3f vecLine;
    private final float length;

    public SDFLine(float x1, float y1, float z1, float x2, float y2, float z2, float r1, float r2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.r1 = r1;
        this.r2 = r2;

        this.pos1 = new Vec3f(x1, y1, z1);
        this.pos2 = new Vec3f(x2, y2, z2);
        this.vecLine = new Vec3f(x2 - x1, y2 - y1, z2 - z1);
        this.length = vecLine.length();
    }

    public SDFLine(float x1, float y1, float z1, float x2, float y2, float z2, float r) {
        this(x1, y1, z1, x2, y2, z2, r, r);
    }

    public SDFLine disableRoundCaps() {
        this.roundCaps = false;
        return this;
    }

    @Override
    public float distance(float x, float y, float z) {
        Vec3f pos = new Vec3f(x ,y, z);
        Vec3f vecPoint = pos.subtract(pos1);
        float proj = vecPoint.dot(vecLine) / length;
        float ratio = proj / length;

        if (!MathHelper.isInRange(ratio, 0, 1, true)) {
            if (!roundCaps) {
                return 0; // not technically correct but it works
            }

            if (ratio < 0) {
                return pos.distance(pos1) - r1;
            } else if (ratio > 1) {
                return pos.distance(pos2) - r2;
            }
        }

        float r = MathHelper.lerp(MathHelper.clamp(ratio, 0, 1), r1, r2);
        float distance = vecLine.normalize().multiply(proj).distance(vecPoint);
        return distance - r;
    }
}
