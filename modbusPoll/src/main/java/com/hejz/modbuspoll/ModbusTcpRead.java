package com.hejz.modbuspoll;

import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;

import java.util.HashMap;
import java.util.Map;

public class ModbusTcpRead {
    public static Map<Integer, Object> extracted(ModbusMaster master, int slaveId, int offset, int quantity, ResultType type) throws Exception {
        Map<Integer, Object> map = new HashMap<>();
//            int slaveId = 1;//从机地址
//            int offset = 0;//寄存器读取开始地址
//            int quantity = 120;//读取的寄存器数量
//                System.out.println("读取对应从机的数据");
        //读取对应从机的数据，这里演示的是功能码03
        //功能码01 readCoils()——一般01码区有安全顾虑，不开放的，如果开放就要按规则验证，这个数据一般不取的。
        //功能码02 readDiscreteInputs()
        if (type == null) {
            boolean[] booleans = master.readDiscreteInputs(slaveId, offset, quantity);
            for (int i = 0; i < quantity; i++) {
//                System.out.println("读取2X参数" + i + "===" + booleans[i]);
                map.put(i,booleans[i]);
            }
        }
        //功能码03 readHoldingRegisters()
        //功能码04 readInputRegisters()
        int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
        for (int i = 0; i < registerValues.length - 1; i++) {
            int value1 = registerValues[i];
            int value2 = registerValues[i + 1];
            if(type==null)break;
            //转换数据类型
            switch (type) {
                case SIGNED_INTEGER:
                    break;
                case UNSIGNED_INTEGER:
                    break;
                case FLOAT:
                    // 转换为浮点数数组
                    float[] floatValues = new float[quantity / 2];
                    for (int i1 = 0; i1 < quantity; i1 += 2) {
                        int value = registerValues[i1] << 16 | registerValues[i1 + 1];
                        floatValues[i1 / 2] = Float.intBitsToFloat(value);
                    }
                    float aFloat = parseModbusFloat(value1, value2);
                    //转化为浮点数值
                    if (i % 2 == 0) {
                        map.put(i, aFloat);
                    }
                    break;
                case LONG:
//                    Long[] longValues = new Long[quantity/2];
                    for (int i1 = 0; i1 < quantity; i1 += 2) {
                        long value = (((long) registerValues[i1]) << 16) | (registerValues[i1 + 1] & 0xFFFF);
//                        longValues[i1] = value;
                        if (i1 % 2 == 0) {
                            map.put(i1, value);
                        }
                    }
                    break;
                case DOUBLE:
                    break;
                default:
                    throw new IllegalArgumentException("不支持的数据类型");
            }
        }
        return map;  //整数为两个值
    }

    // 解析 Modbus 浮点数——主要在前，次位在后，两个bytes为一个浮点数
    private static float parseModbusFloat(int valueHigh, int valueLow) {
        int combinedValue = (valueHigh << 16) | valueLow;
        return Float.intBitsToFloat(combinedValue);
    }
}
