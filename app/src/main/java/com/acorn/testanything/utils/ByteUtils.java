package com.acorn.testanything.utils;

public class ByteUtils {
    private static final char[] HEX_CHAR_ARR = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    private static final String HEX_STR = "0123456789ABCDEF";


    /**
     * 字节数组字符串
     * @param btArr  字节数组
     * @return 16进制字符串
     */
    public static String byteArrToHex(byte[] btArr) {
        char[] strArr = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HEX_CHAR_ARR[bt>>>4 & 0xf];
            strArr[i++] = HEX_CHAR_ARR[bt & 0xf];
        }
        return new String(strArr);
    }

    /**
     * 16进制字符串转字节数据
     * @param hexStr  字符串
     * @return 字节数组
     */
    public static byte[] hexToByteArr(String hexStr) {
        char[] charArr = hexStr.toCharArray();
        byte[] btArr = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HEX_STR.indexOf(charArr[i]);
            int lowBit = HEX_STR.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }

    // 16进制的字符串表示转成字节数组
    public static byte[] toByteArrayBigOrder(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
