package com.hejz.modbuspoll;

public class ModbusDataParser {
    public static void main(String[] args) {
        String data = "01 04 04 BE B8 51 EC 63 94";

        float[] floatValues = ModbusDataParser.parseModbusData(data);

        for (float value : floatValues) {
            System.out.println("Float Value: " + value);
        }
    }

    public static float[] parseModbusData(String data) {
        // 将数据字符串拆分为字节
        String[] bytes = data.split(" ");

        // 校验数据长度
        int dataLength = Integer.parseInt(bytes[2], 16);
        if (bytes.length < 3 + dataLength + 2) {
            throw new IllegalArgumentException("数据长度不匹配");
        }

        // 计算校验和
        int checksum = 0;
        for (int i = 0; i < bytes.length - 2; i++) {
            checksum += Integer.parseInt(bytes[i], 16);
        }
        checksum = checksum & 0xFFFF; // 保留低16位

        // 检查校验和是否匹配
//        int receivedChecksum = Integer.parseInt(bytes[bytes.length - 2], 16) << 8;
//        receivedChecksum |= Integer.parseInt(bytes[bytes.length - 1], 16);
//        if (checksum != receivedChecksum) {
//            throw new IllegalArgumentException("校验和不匹配");
//        }

        // 解析浮点数数据
        float[] floatValues = new float[dataLength / 4];
        for (int i = 0; i < dataLength; i += 4) {
            int intValue = 0;
            for (int j = 0; j < 4; j++) {
                intValue <<= 8;
                intValue |= Integer.parseInt(bytes[3 + i + j], 16);
            }
            floatValues[i / 4] = Float.intBitsToFloat(intValue);
        }

        return floatValues;
    }
}
