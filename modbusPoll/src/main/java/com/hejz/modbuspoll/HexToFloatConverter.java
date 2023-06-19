package com.hejz.modbuspoll;

import java.math.BigInteger;

/**
 * 冷量表的计算方法
 */
public class HexToFloatConverter {
    public static void main(String[] args) {
        //E148AFFE
        //DD2F871D
        //AE146203
        //0000EFB2
        String hexData = "00610000";
        float result = hexToFloat(hexData);
        System.out.println("HexToFloat结果：" + result);
        int  UnsignedShort = hexStringToUnsignedShort(hexData);
        System.out.println("hexStringToUnsignedShort结果：" + UnsignedShort);
        long longInverse = hexToLongInverse(hexData);
        System.out.println("hexToLong结果：" + longInverse);
    }

    public static float hexToFloat(String hexData) {
        int intValue = new BigInteger(hexData, 16).intValue();
        return Float.intBitsToFloat(intValue);
    }

    public static int  hexStringToUnsignedShort(String hexString) {
        // 移除前导的0字符
        hexString = hexString.replaceFirst("^0+", "");

        // 将十六进制字符串转换为整型
        int intValue = Integer.parseInt(hexString, 16);

        // 将整型值限制在无符号短整型的范围内
        return intValue & 0xFFFF;
    }
    public static long hexToLongInverse(String hexString) {
        // 去除字符串中的空格
        hexString = hexString.replace(" ", "");

        // 将16进制字符串转换为长整型
        long longValue = Long.parseLong(hexString, 16);

        return longValue;
    }
}
