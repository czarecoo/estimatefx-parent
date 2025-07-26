package com.czareg.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}