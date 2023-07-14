package com.hejz.modbuspoll;

/**
 * 一个传感器过来的值高低位转化为10进制，读取寄存器后高位值*65536+低位值后的十进制结果，
 * 请根据此运算方法，把现在计算得到的10进制数据逆向生成两个高低位16进制字符串，
 * 请根据此计算后的数据逆向生成高低位数据，写一个java算法
 */
public class HexConversion {
    public static void main(String[] args) {
        // 逆向计算示例
        long decimalValue = 12345678; // 假设得到的10进制数据

        // 计算高低位值
        long highValue = decimalValue / 65536;
        long lowValue = decimalValue % 65536;

        // 转换为16进制字符串
        String highHex = Long.toHexString(highValue).toUpperCase();
        String lowHex = Long.toHexString(lowValue).toUpperCase();

        // 补齐高低位字符串长度为4
        highHex = String.format("%4s", highHex).replace(' ', '0');
        lowHex = String.format("%4s", lowHex).replace(' ', '0');


        // 合并高低位16进制字符串
        String hexString = highHex + lowHex;

        // 将16进制字符串转换为float
        long hexLong = Long.parseLong(hexString, 16);
        float floatValue = Float.intBitsToFloat((int) hexLong);

        // 输出结果
        System.out.println("合并后的16进制字符串: " + hexString);
        System.out.println("转换为float后的值: " + floatValue);
    }
}
