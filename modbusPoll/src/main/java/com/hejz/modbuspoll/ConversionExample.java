package com.hejz.modbuspoll;

public class ConversionExample {
    public static void main(String[] args) {
        float floatValue = 1.234f;
        double doubleValue = convertToDouble(floatValue);
        System.out.println(doubleValue);
    }

    public static double convertToDouble(float floatValue) {
        return (double) floatValue;
    }
}
