package org.sdoaj.jimgus.util;

import java.util.Random;

public class Util {
    public static <T> T pickRandom(Random random, T[] array) {
        return array[random.nextInt(array.length)];
    }
}
