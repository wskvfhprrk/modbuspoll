package com.hejz.modbuspoll;

import java.text.DecimalFormat;

public class ResponseParser {
    public static void main(String[] args) {
        String responseHex = "0104043E4CCCCDA32E";
        float result = parseFloat(responseHex);
        System.out.println("浮点数为："+result);
        System.out.println("保留两位小数结果："+formatDouble((double) result));
    }

    public static float parseFloat(String hexString) {
        // 将16进制字符串转换为字节数组
        byte[] data = hexStringToByteArray(hexString);

        // 解析浮点数的各个部分
        int sign = (data[3] & 0x80) != 0 ? -1 : 1;
        int exponent = ((data[3] & 0x7F) << 1) | ((data[4] & 0x80) >> 7);
        int mantissa = ((data[4] & 0x7F) << 16) | (data[5] << 8) | (data[6] & 0xFF);

        // 计算浮点数的值
        float result = (float) (sign * Math.pow(2, exponent - 127) * (1 + mantissa / Math.pow(2, 23)));

        return result;
    }

    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                                + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    public static String formatDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value);
    }
}
