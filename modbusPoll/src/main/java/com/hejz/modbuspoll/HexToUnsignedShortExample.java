package com.hejz.modbuspoll;

public class HexToUnsignedShortExample {
    public static void main(String[] args) {
        String hexString = "00013A44\n".replaceAll("\n","");
        int unsignedShortValue = hexToUnsignedShort(hexString);
        System.out.println(unsignedShortValue);
    }

    public static int hexToUnsignedShort(String hexString) {
        try {
            long decimalValue = Long.parseLong(hexString, 16);
            if (decimalValue >= 0 && decimalValue <= 65535) {
                return (int) decimalValue;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
