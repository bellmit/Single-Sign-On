package com.daop.sso.common.utils;

/**
 * @BelongsProject: iot
 * @BelongsPackage: com.xinye.iot.common.utils
 * @Description: Byte相关工具类
 * @DATE: 2020-07-23
 * @AUTHOR: Administrator
 **/
public class ByteUtil {

    /**
     * Byte数组转int
     *
     * @param byteList Byte数组
     * @return int值
     */
    public static int byte2int(byte[] byteList) {
        int result = 0;
        for (int i = 0; i < byteList.length; i++) {
            result |= (byteList[i] & 0xff) << (8 * i);
        }
        return result;
    }

    /**
     * int转化为Byte数组
     *
     * @param data 数据
     * @return byte数组
     */
    public static byte[] int2byte(int data) {
        byte[] bytes = new byte[4];
        //通过移位运算，截取低8位的方式，将int保存到byte数组
        bytes[0] = (byte) (data >>> 24);
        bytes[1] = (byte) (data >>> 16);
        bytes[2] = (byte) (data >>> 8);
        bytes[3] = (byte) data;
        return bytes;
    }

    /**
     * Byte数组转Float
     *
     * @param byteList Byte数组
     * @return float值
     */
    public static float byte2float(byte[] byteList) {
        int result = 0;
        for (int i = 0; i < byteList.length; i++) {
            result |= (byteList[i] & 0xff) << (8 * i);
        }
        return Float.intBitsToFloat(result);
    }
}
