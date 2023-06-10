package com.hejz.modbuspoll;

/**
 * 十六进制字符串为无符号短
 */
public class HexStringToUnsignedShort {

    public static void main(String[] args) {
        String hexString = "00 01 00 00";
        char unsignedShort = hexStringToUnsignedShort(hexString);
        System.out.println("Unsigned short value: " + (int) unsignedShort);
    }

    public static char hexStringToUnsignedShort(String hexString) {
        // 移除空格
        hexString = hexString.replace(" ", "");

        // 确保输入的16进制字符串长度为4个字符
        if (hexString.length() != 8) {
            throw new IllegalArgumentException("Invalid hex string length.");
        }

        // 将16进制字符串转换为字节数组
        byte[] bytes = new byte[2];
        for (int i = 0; i < 2; i++) {
            int startIndex = i * 2;
            int endIndex = startIndex + 2;
            String byteString = hexString.substring(startIndex, endIndex);
            bytes[i] = (byte) Integer.parseInt(byteString, 16);
        }

        // 将字节数组转换为char类型（unsigned short）
        char unsignedShort = (char) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));

        return unsignedShort;
    }
}