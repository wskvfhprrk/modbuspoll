package com.hejz.modbuspoll;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * java IO复习一下
 */
public class FileStreamTest {
    public static void main(String[] args) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            File inputFile = new File("input.txt");  // 创建一个指向输入文件的File对象
            File outputFile = new File("output.txt");  // 创建一个指向输出文件的File对象
            inputStream = new FileInputStream(inputFile);  // 创建一个文件输入流对象，用于读取输入文件
            outputStream = new FileOutputStream(outputFile);  // 创建一个文件输出流对象，用于写入输出文件
            byte[] buffer = new byte[1024];  // 创建一个字节数组作为缓冲区
            int length;
            while ((length = inputStream.read(buffer)) > 0) {  // 读取输入流中的数据到缓冲区，并返回读取的字节数
                outputStream.write(buffer, 0, length);  // 将缓冲区中的数据写入输出流
            }
        } catch (IOException e) {
            e.printStackTrace();  // 打印异常堆栈跟踪信息
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();  // 关闭输入流
                }
                if (outputStream != null) {
                    outputStream.close();  // 关闭输出流
                }
            } catch (IOException e) {
                e.printStackTrace();  // 打印异常堆栈跟踪信息
            }
        }
    }
}