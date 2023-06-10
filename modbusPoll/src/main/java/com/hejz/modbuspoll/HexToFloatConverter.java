package com.hejz.modbuspoll;

import java.nio.ByteBuffer;

public class HexToFloatConverter {
    public static float hexToFloat(String hexString) {
        // 去除字符串中的空格
        hexString = hexString.replace(" ", "");

        // 将字符串转换为字节数组
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            bytes[i] = (byte) Integer.parseInt(hexString.substring(index, index + 2), 16);
        }

        // 将字节数组转换为浮点数
        float floatValue = ByteBuffer.wrap(bytes).getFloat();

        return floatValue;
    }

    public static void main(String[] args) {
        String hexString = "00 00 00 00".replaceAll(" ", "");

        float floatValue = HexToFloatConverter.hexToFloat(hexString);

        System.out.println("Float Value: " + floatValue);
    }
}
