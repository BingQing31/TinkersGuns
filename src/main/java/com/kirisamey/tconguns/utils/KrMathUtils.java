package com.kirisamey.tconguns.utils;

public class KrMathUtils {
    // 你敢想象吗？Jvav这脑残语言直到v21才加入clamp方法。我真气笑了
    // Jvav：为什么大家都这么讨厌我，明明我什么都没做啊？
    // 答：在还活着的语言里你做了的就只比C语言多了
    public static double clamp(double value, double min, double max) {
        if (min > max) throw new IllegalArgumentException("Min cannot be greater than Max");
        return Math.min(max, Math.max(min, value));
    }

    public static float clampF(float value, float min, float max) {
        if (min > max) throw new IllegalArgumentException("Min cannot be greater than Max");
        return Math.min(max, Math.max(min, value));
    }
}
