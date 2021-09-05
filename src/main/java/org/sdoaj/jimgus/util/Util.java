package org.sdoaj.jimgus.util;

import java.util.List;
import java.util.Random;

public class Util {
    public static <T> T pickRandom(Random random, T[] array) {
        return array[random.nextInt(array.length)];
    }

    public static <T> T pickRandom(Random random, List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
