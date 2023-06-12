package com.hejz.modbuspoll;

public class ModbusProtocolParser {
    public static void main(String[] args) {
        byte[] responseData = {0x01, 0x03, 0x04, 0x00, 0x0A, 0x00, 0x0B, 0x00, 0x0C};
        ModbusProtocolParser.parseModbusResponse(responseData);

    }
    public static void parseModbusResponse(byte[] responseData) {
        // 检查响应数据的有效性
        if (responseData.length < 5) {
            System.out.println("无效的Modbus响应数据！");
            return;
        }

        // 解析功能码
        int functionCode = responseData[1] & 0xFF;

        // 根据功能码进行解析
        switch (functionCode) {
            case 3:
                // 解析读取保持寄存器响应
                int numRegisters = responseData[2] & 0xFF;
                System.out.println("读取到 " + numRegisters + " 个保持寄存器值：");
                for (int i = 0; i < numRegisters; i++) {
                    int registerValue = (responseData[3 + i * 2] & 0xFF) << 8 |
                                        (responseData[4 + i * 2] & 0xFF);
                    System.out.println("寄存器" + (i + 1) + ": " + registerValue);
                }
                break;
            case 16:
                // 解析写入多个保持寄存器响应
                int startAddress = (responseData[2] & 0xFF) << 8 |
                                    (responseData[3] & 0xFF);
                int numWritten = (responseData[4] & 0xFF) << 8 |
                                    (responseData[5] & 0xFF);
                System.out.println("成功写入 " + numWritten + " 个保持寄存器，起始地址：" + startAddress);
                break;
            default:
                System.out.println("不支持的Modbus功能码：" + functionCode);
                break;
        }
    }
}
