package org.sdoaj.jimgus.util;

import java.util.Random;

public abstract class LSystem {
    protected abstract String getSeed();

    protected abstract String produce(char c, Random random);

    public String run(int iterations, Random random) {
        String string = getSeed();

        for (int i = 0; i < iterations; i++) {
            char[] chars = string.toCharArray();
            StringBuilder newString = new StringBuilder();

            for (char c : chars) {
                newString.append(produce(c, random));
            }

            string = newString.toString();
        }

        return string;
    }

    public String run(int iterations) {
        return run(iterations, null);
    }
}
