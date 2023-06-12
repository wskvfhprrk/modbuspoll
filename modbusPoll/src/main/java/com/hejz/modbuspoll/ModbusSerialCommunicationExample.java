package com.hejz.modbuspoll;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ModbusSerialCommunicationExample {

    public static void main(String[] args) {
        // 获取串口列表
        SerialPort[] ports = SerialPort.getCommPorts();

        // 选择要连接的串口
        SerialPort port = ports[0];
        port.setBaudRate(9600);  // 设置波特率
        port.setNumDataBits(8);  // 设置数据位数
        port.setNumStopBits(1);  // 设置停止位数
        port.setParity(SerialPort.NO_PARITY);  // 设置校验位
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);  // 设置读取超时时间

        // 打开串口
        if (port.openPort()) {
            System.out.println("串口已打开！");
        } else {
            System.out.println("串口打开失败！");
            return;
        }

        // 获取输入输出流
        InputStream inputStream = port.getInputStream();
        OutputStream outputStream = port.getOutputStream();

        // 准备modbus协议数据
//        byte[] requestData = new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x01, 0x85, (byte) 0xC5};
              String message="01 03 03 01 00 01 D5 8E"; //湿度数据
//        String message = "01 03 03 00 00 01 84 4E"; //温度数据
        System.out.println("发送数据：" + message);
        byte[] requestData = hexStringToBytes(message.replaceAll(" ", ""));  // 将字符串形式的请求数据转换为字节数组
        try {
            // 发送modbus请求
            outputStream.write(requestData);
            outputStream.flush();

            // 等待数据的到来
            while (inputStream.available() <= 0) {
                Thread.sleep(10);
            }

            // 读取响应数据
            byte[] responseData = new byte[inputStream.available()];
            inputStream.read(responseData);

            // 处理响应数据
            System.out.println("收到响应数据：");
            for (int i = 0; i < responseData.length; i++) {
                System.out.print(String.format("%02X", responseData[i]) + " ");
            }
            System.out.println();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭串口
            port.closePort();
            System.out.println("串口已关闭！");
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.replaceAll(" ", ""); // 移除字符串中的空格
        int length = hexString.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string"); // 非偶数长度的十六进制字符串无效
        }
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            String hex = hexString.substring(i, i + 2);
            bytes[i / 2] = (byte) Integer.parseInt(hex, 16); // 解析十六进制字符串并转换为字节
        }
        return bytes;
    }

}