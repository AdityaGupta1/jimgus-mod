package org.sdoaj.jimgus.util.sdf.operators;

// like a union but source A is the "main" source and is used anywhere its distance is < 0
public class SDFAdd extends SDFUnion {
    @Override
    protected void selectValue(float a, float b) {
        firstValue = a < 0;
    }
}
