package com.gentle.cache.utils;

import java.math.BigInteger;

/**
 * 数据类型转换器
 *
 * @author Gentle
 * @date 2019/06/21 : 12:40
 */
public class ConversionUtils {

    /**
     * 类型转换器
     *
     * @param source
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> T convert(String source, Class<T> targetType) {
        if (targetType == Integer.class || targetType == int.class) {
            return (T) (isHexNumber(source) ? Integer.decode(source) : Integer.valueOf(source));
        } else if (targetType == Double.class || targetType == double.class) {
            return (T) Double.valueOf(source);
        } else if (targetType == Byte.class || targetType == byte.class) {
            return (T) (isHexNumber(source) ? Byte.decode(source) : Byte.valueOf(source));
        } else if (targetType == Float.class || targetType == float.class) {
            return (T) Float.valueOf(source);
        } else if (targetType == Short.class || targetType == short.class) {
            return (T) (isHexNumber(source) ? Short.decode(source) : Short.valueOf(source));
        } else if (targetType == Long.class || targetType == long.class) {
            return (T) (isHexNumber(source) ? Long.decode(source) : Long.valueOf(source));
        } else if (targetType == BigInteger.class) {
            return (T) (isHexNumber(source) ? decodeBigInteger(source) : new BigInteger(source));
        } else if ((targetType == String.class)) {
            return (T) String.valueOf(source);
        }
        throw new IllegalArgumentException("数据类型类型不支持");
    }

    /**
     * @param value
     * @return
     */
    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
    }

    /**
     * @param value
     * @return
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        // Handle minus sign, if present.
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        // Handle radix specifier, if present.
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }


    public static boolean isBaseDefaultValue(Class targetType) {
        if (targetType == Integer.class || targetType == int.class) {
            return true;
        } else if (targetType == Double.class || targetType == double.class) {
            return true;
        } else if (targetType == Byte.class || targetType == byte.class) {
            return true;
        } else if (targetType == Float.class || targetType == float.class) {
            return true;
        } else if (targetType == Short.class || targetType == short.class) {
            return true;
        } else if (targetType == Long.class || targetType == long.class) {
            return true;
        } else if (targetType == BigInteger.class) {
            return true;
        } else if (targetType == String.class) {
            return true;
        } else {
            return false;
        }
    }

}