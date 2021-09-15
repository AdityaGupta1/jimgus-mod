package org.sdoaj.jimgus.util;

public abstract class LSystem {
    protected abstract String getSeed();

    protected abstract String produce(char c);

    public String run(int iterations) {
        String string = getSeed();

        for (int i = 0; i < iterations; i++) {
            char[] chars = string.toCharArray();
            StringBuilder newString = new StringBuilder();

            for (char c : chars) {
                newString.append(produce(c));
            }

            string = newString.toString();
        }

        return string;
    }
}
