package com.hejz.modbuspoll;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;

import java.net.InetAddress;
import java.util.Map;

public class ModbusTest {
    public static void main(String[] args) throws Exception {
        TcpParameters tcpParameters = new TcpParameters();
        // 设置TCP的ip地址-本地地址
        InetAddress address = InetAddress.getByName("172.30.15.101");
        // tcpParameters.setHost(InetAddress.getLocalHost());
        tcpParameters.setHost(address);

        // TCP设置长连接
        tcpParameters.setKeepAlive(true);
        // TCP设置端口，这里设置是默认端口502
        tcpParameters.setPort(502);
        System.out.println("准备执行开启连接");
        // 创建一个主机
        ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
        Modbus.setAutoIncrementTransactionId(true);
        if (!master.isConnected()) {
            master.connect();// 开启连接
        }
        for (int i = 0; i < 7; i++) {
            try {
                Map<Integer, Object> map = ModbusTcpRead.extracted(master, 1, i * 100, 100, ResultType.FLOAT);
                for (Map.Entry<Integer, Object> entry : map.entrySet()) {
//                    System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                    System.out.println(i+","+entry.getKey()+","+entry.getValue());
                }
            }catch (Exception e){
                if(e.getMessage().equals("ILLEGAL_DATA_ADDRESS: Exception Code = 2")){
                    System.out.println(i+",没有数据，不能解析,");
//                    System.out.println("address=============="+i+" ；没有数据，不能解析");
                    continue;
                }
                e.printStackTrace();
            }
        }for (int i = 150; i < 154; i++) {
            try {
                Map<Integer, Object> map = ModbusTcpRead.extracted(master, 1, i * 100, 100, ResultType.INT);
                for (Map.Entry<Integer, Object> entry : map.entrySet()) {
//                    System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                    System.out.println(i+","+entry.getKey()+","+entry.getValue());
                }
            }catch (Exception e){
                if(e.getMessage().equals("ILLEGAL_DATA_ADDRESS: Exception Code = 2")){
                    System.out.println(i+",没有数据，不能解析,");
//                    System.out.println("address=============="+i+" ；没有数据，不能解析");
                    continue;
                }
                e.printStackTrace();
            }
        }

    }

}
