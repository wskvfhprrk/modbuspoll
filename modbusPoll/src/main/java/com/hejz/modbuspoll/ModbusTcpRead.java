package com.hejz.modbuspoll;

import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;

import java.util.HashMap;
import java.util.Map;

public class ModbusTcpRead {
    public static Map<Integer,Object> extracted(ModbusMaster master, int slaveId, int offset, int quantity, ResultType type) throws Exception {
//            int slaveId = 1;//从机地址
//            int offset = 0;//寄存器读取开始地址
//            int quantity = 120;//读取的寄存器数量
//                System.out.println("读取对应从机的数据");
        //读取对应从机的数据，这里演示的是功能码03
        //功能码01 readCoils()——一般01码区有安全顾虑，不开放的，如果开放就要按规则验证，这个数据一般不取的。
        //功能码02 readDiscreteInputs()
//                boolean[] booleans = master.readDiscreteInputs(slaveId, offset, quantity);
//                for (int i = 0; i < booleans.length; i++) {
//                    System.out.println("读取2X参数"+i+"==="+booleans[i]);
//                }
        //功能码03 readHoldingRegisters()
        //功能码04 readInputRegisters()
        int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
        Map<Integer,Object> map=new HashMap<>();
        for (int i = 0; i < registerValues.length - 1; i++) {
            int key = registerValues[i];
            int value = registerValues[i + 1];
            //转换数据类型
            if (type.ordinal() == 0) {
                float aFloat = parseModbusFloat(key, value);
                //                    System.out.println("读取到4X区普通数："+i+"="+value);
                //转化为浮点数值
                if (i % 2 == 0) {
                    map.put(i,aFloat);
                }
            } else if (type.ordinal() == 1) {
                map.put(i,value);
            }

        }
        return map;

    }
    // 解析 Modbus 浮点数——主要在前，次位在后，两个bytes为一个浮点数
    private static float parseModbusFloat(int valueHigh, int valueLow) {
        int combinedValue = (valueHigh << 16) | valueLow;
        return Float.intBitsToFloat(combinedValue);
    }
}
