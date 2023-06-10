package com.hejz.modbuspoll;

public class HexToLongInverseConverter {
    public static long hexToLongInverse(String hexString) {
        // 去除字符串中的空格
        hexString = hexString.replace(" ", "");

        // 将16进制字符串转换为长整型
        long longValue = Long.parseLong(hexString, 16);

        return longValue;
    }
    public static void main(String[] args) {
        String hexString = "00 05 88 53";

        long longValue = HexToLongInverseConverter.hexToLongInverse(hexString);

        System.out.println("Long Value: " + longValue);
    }
}
