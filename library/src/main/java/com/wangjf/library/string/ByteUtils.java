package com.wangjf.library.string;

import android.content.Context;
import android.util.Log;

import com.genvict.library.R;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangjf on 2018/3/21.
 */

public class ByteUtils {

    private static final String LOG_TAG = "Genvict";

    /**
     * int to byte array
     * 小端模式int转byte数组
     * obuBean.setObuRSSI 固定使用小端int
     *
     * @param i
     * @return
     */
    public static byte[] littleIntToBytes(int i) {
        byte[] abyte = new byte[4];

        abyte[0] = (byte) (0xff & i);
        abyte[1] = (byte) ((0xff00 & i) >> 8);
        abyte[2] = (byte) ((0xff0000 & i) >> 16);
        abyte[3] = (byte) ((0xff000000 & i) >> 24);

        return abyte;
    }

    /**
     * 大端模式int转byte数组
     *
     * @param i
     * @return
     */
    public static byte[] bigIntToBytes(int i) {
        byte[] abyte = new byte[4];

        abyte[0] = (byte) ((i >> 24) & 0xff);
        abyte[1] = (byte) ((i >> 16) & 0xff);
        abyte[2] = (byte) ((i >> 8) & 0xff);
        abyte[3] = (byte) (i & 0xff);

        return abyte;
    }

    /**
     * byte array to int
     * byte数组转小端模式int
     *obuBean.setObuRSSI 固定使用小端int
     *
     * @param bytes
     * @return
     */
    public static int bytesToLittleInt(byte[] bytes) {
        int tmp = 0;

        tmp = bytes[0] & 0xFF;
        tmp |= (((int) bytes[1] << 8) & 0xFF00);
        tmp |= (((int) bytes[2] << 16) & 0xFF0000);
        tmp |= (((int) bytes[3] << 24) & 0xFF000000);

        return tmp;
    }

    /**
     * byte数组转大端模式int
     *
     * @param bytes
     * @return
     */
    public static int bytesToBigInt(byte[] bytes) {
        int tmp = 0;

        tmp = bytes[0] & 0xFF;
        tmp = (tmp << 8) | (bytes[1] & 0xff);
        tmp = (tmp << 8) | (bytes[2] & 0xff);
        tmp = (tmp << 8) | (bytes[3] & 0xff);

        return tmp;
    }

    /**
     * 4字节转long
     *
     * @param bytes
     * @return
     */
    public static long fourBytesToLong(byte[] bytes) {
        int firstByte = 0;
        int secondByte = 0;
        int thirdByte = 0;
        int fourthByte = 0;
        int index = 0;

        firstByte = (0x000000FF & ((int) bytes[index++]));
        secondByte = (0x000000FF & ((int) bytes[index++]));
        thirdByte = (0x000000FF & ((int) bytes[index++]));
        fourthByte = (0x000000FF & ((int) bytes[index++]));

        return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
    }

    /**
     * 整数到字节数组转换
     *
     * @param n
     * @return
     */
    public static byte[] intToBytes(int n) {
        byte[] ab = new byte[4];

        ab[0] = (byte) (0xff & n);
        ab[1] = (byte) ((0xff00 & n) >> 8);
        ab[2] = (byte) ((0xff0000 & n) >> 16);
        ab[3] = (byte) ((0xff000000 & n) >> 24);

        return ab;
    }

    /**
     *  小端整数到字节数组转换
     *  设备请求控制指令需用到小端证书转字节数组
     *
     * @param n
     * @return
     */
    public static byte[] littleIntToBytes2(int n) {
        byte[] ab = new byte[2];

        ab[0] = (byte) (0xff & n);
        ab[1] = (byte) ((0xff00 & n) >> 8);

        return ab;
    }

    /**
     *  大端整数到字节数组转换
     * @param n
     * @return
     */
    public static byte[] BigIntToBytes2(int n) {
        byte[] ab = new byte[2];

        ab[1] = (byte) (0xff & n);
        ab[0] = (byte) ((0xff00 & n) >> 8);

        return ab;
    }

    /**
     *  byte数组转大端模式int
     * @param bytes
     * @return int
     */
    public static int bytes2TobigInt(byte[] bytes) {
        int tmp = 0;

        tmp = bytes[0] & 0xFF;
        tmp = (tmp << 8) | (bytes[1] & 0xff);

        return tmp;
    }

    /**
     *  byte数组转小端模式int
     * @param bytes
     * @return int
     */
    public static int bytes2ToLittleInt(byte[] bytes) {
        int tmp = 0;

        tmp = bytes[1] & 0xFF;
        tmp = (tmp << 8) | (bytes[0] & 0xff);

        return tmp;
    }

    /**
     * 省份编码十六进制转化为十进制显示使用
     *
     * @param hexString
     * @return
     */
    public static String hexStringToIntString(String hexString)
    {
        if (hexString == null || hexString.equals("")) {
            return "";
        }

        String number = "";
        for(int i = 0; i < hexString.length() / 2; i++)
        {
            number += String.format("%02d", Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16));
        }
        return number;
    }

    /**
     * 字节数组到整数的转换
     *
     * @param b
     * @return
     */
    public static int bytesToInt(byte b[]) {
        int s = 0;
        s = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8 | (b[3] & 0xff);

        return s;
    }


    private final static byte[] hex = "0123456789ABCDEF".getBytes();
    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /**
     * 从字节数组到十六进制字符串转换
     *
     * @param b
     * @param bytesLen
     * @return
     */
    public static String bytesToHexString(byte[] b, int bytesLen) {
        byte[] buff = new byte[2 * bytesLen];
        for (int i = 0; i < bytesLen; i++) {
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 从十六进制字符串到字节数组转换
     *
     * @param hexstr
     * @return
     */
    public static byte[] hexStringToBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * 字节数组转字符串
     *
     * @param buf
     * @param index
     * @param num
     * @return
     */
    public static String byteToString(byte[] buf, int index, int num)
    {
        int i = 0;

        if( buf[index] == 0){
            return null;
        }

        for(i=0; i<num; i++)
        {
            if( buf[index+i] == 0 ){
                break;
            }
        }

        byte[] dest = new byte[i];
        System.arraycopy(buf, index, dest, 0, i);

        String s = null;
        try {
            s = new String(dest,"gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 日期格式从：20130203 －》2013年02月03日
     *
     * @param HexDate
     * @return
     */
    public static String bcdDateToStr(String HexDate) {
        int index = 0;
        char[] Date = new char[12];
        char ch;
        Log.i(LOG_TAG, "DateHexToStr0:" + HexDate);
        for(int i=0;i<HexDate.length();i++)
        {
            ch = HexDate.charAt(i);

            if( ch>='0' && ch<='9' )
            {
                Date[index++] = ch;
            }
            if( index == 4 ) Date[index++] = '年';
            if( index == 7 ) Date[index++] = '月';
            if( index == 10 ) Date[index++] = '日';
        }
        if( index != 11 ) return null;
        String Str = new String(Date, 0, 11);
        return Str;
    }

    /**
     *
     * @param buf
     * @param num
     */
    public static void memset(byte[] buf, int num)
    {
        for(int i=0;i<num;i++)
            buf[i] = 0;
    }

    /**
     *
     * @param str
     * @return
     */
    public static byte[] stringToByte(String str)
    {
        byte[] b = null;
        int buf_len = 0;
        try {
            b = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("stringToByte:"+str+",length="+b.length);
        return b;
    }

    /**
     *
     * @param bin
     * @param index
     * @param len
     * @return
     */
    public static String binToHex(byte[] bin, int index, int len)
    {
        int b;
        byte[] hex_str = new byte[len*2];
        int index_str = 0;
        byte[] bin2hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String str = null;

        if( len <= 0 ) return null;
        for(int i=0;i<len;i++)
        {
            if( bin[index] >= 0 ) {
                //*bin ++;
                b  = bin[index];
            } else{
                b = 256 + bin[index];
            }
            index++;
            hex_str[index_str++] = bin2hex[b >> 4];
            hex_str[index_str++] = bin2hex[b & 0xf];
        }
        try {
            str = new String(hex_str, "GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("binToHex:" + str);
        hex_str = null;
        return str;
    }

    /**
     *
     * @param data
     * @param len
     * @return
     */
    public static byte getBcc(byte[] data, int len)
    {
        byte bcc = 0x00;
        int i;
        for(i = 0; i < len; i++)
        {
            bcc ^= data[i];
        }
        return bcc;
    }

    /**
     * 字符串转换为Ascii
     * @param value
     * @return
     */
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            sbu.append((int)chars[i]);
        }

        return sbu.toString();
    }

    /**
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     * @param strPart
     *            字符串
     * @return 16进制字符串
     * @throws
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     *
     * @param context
     * @param strPlate_color
     * @return
     */
    public static String stringPlateColor2HexString(Context context, String strPlate_color) {
        int nPlate_color = 0;
        String[] colors = context.getResources().getStringArray(R.array.plateColor);
        while (!strPlate_color.equals(colors[nPlate_color])) {
            Log.i("nPlate_color", "nPlate_color: " + nPlate_color);
            nPlate_color++;
        }
        return String.format("%02x", nPlate_color);
    }

    /**
     *
     * @param context
     * @param strPlate_color
     * @return
     */
    public static String hexStringPlateColor2String(Context context, String strPlate_color) {
        if (strPlate_color == null || strPlate_color.equals("")) {
            return "";
        }

        String licencePlateColor = "蓝色";
        int nPlate_color = Integer.parseInt(strPlate_color, 16);
        String[] colors = context.getResources().getStringArray(R.array.plateColor);
        if (nPlate_color >=0 && nPlate_color < colors.length) {
            licencePlateColor = colors[nPlate_color];
        } else {
            licencePlateColor = "未定义";
        }
        return licencePlateColor;
    }

    /**
     *
     * @param context
     * @param strType
     * @return
     */
    public static String stringVehicleType2HexString(Context context, String strType) {
        int nType = 0;
        String[] vehicleTypes = context.getResources().getStringArray(R.array.vehicleTypes);
        if(strType.equals(vehicleTypes[0])){
            nType = 1;
        }else if(strType.equals(vehicleTypes[1])){
            nType = 2;
        }else if(strType.equals(vehicleTypes[2])){
            nType = 3;
        }else if(strType.equals(vehicleTypes[3])){
            nType = 4;
        }else if(strType.equals(vehicleTypes[4])){
            nType = 5;
        }else if(strType.equals(vehicleTypes[5])){
            nType = 6;
        }else if(strType.equals(vehicleTypes[6])){
            nType = 11;
        }else if(strType.equals(vehicleTypes[7])){
            nType = 12;
        }else if(strType.equals(vehicleTypes[8])){
            nType = 13;
        }else if(strType.equals(vehicleTypes[9])){
            nType = 14;
        }else if(strType.equals(vehicleTypes[10])){
            nType = 15;
        }else if(strType.equals(vehicleTypes[11])){
            nType = 16;
        }
        return String.format("%02x", nType);
    }

    /**
     *
     * @param context
     * @param strType
     * @return
     */
    public static String hexStringVehicleType2String(Context context, String strType) {
        String vehicleType = "";
        String[] vehicleTypes = context.getResources().getStringArray(R.array.vehicleTypes);
        switch(Integer.parseInt(strType, 16)){
            case 1:
                vehicleType = vehicleTypes[0];
                break;
            case 2:
                vehicleType = vehicleTypes[1];
                break;
            case 3:
                vehicleType = vehicleTypes[2];
                break;
            case 4:
                vehicleType = vehicleTypes[3];
                break;
            case 5:
                vehicleType = vehicleTypes[4];
                break;
            case 6:
                vehicleType = vehicleTypes[5];
                break;
            case 11:
                vehicleType = vehicleTypes[6];
                break;
            case 12:
                vehicleType = vehicleTypes[7];
                break;
            case 13:
                vehicleType = vehicleTypes[8];
                break;
            case 14:
                vehicleType = vehicleTypes[9];
                break;
            case 15:
                vehicleType = vehicleTypes[10];
                break;
            case 16:
                vehicleType = vehicleTypes[11];
                break;
            default:
                vehicleType = "未定义";
                break;
        }
        return vehicleType;
    }

    /**
     *
     * @param context
     * @param strType
     * @return
     */
    public static String stringUserType2HexString(Context context, String strType) {
        int userType = 0;
        String[] userTypes = context.getResources().getStringArray(R.array.userTypes);
        if (strType.equals(userTypes[0])) {
            userType = 0;
        } else if (strType.equals(userTypes[1])) {
            userType = 6;
        } else if (strType.equals(userTypes[2])) {
            userType = 8;
        } else if (strType.equals(userTypes[3])) {
            userType = 10;
        } else if (strType.equals(userTypes[4])) {
            userType = 12;
        } else if (strType.equals(userTypes[5])) {
            userType = 14;
        }
        return String.format("%02x", userType);
    }

    /**
     *
     * @param context
     * @param strType
     * @return
     */
    public static String hexStringUserType2String(Context context, String strType) {
        String userType = "";
        Log.i("hexString", "hexStringUserType2String: " + strType);
        String[] userTypes = context.getResources().getStringArray(R.array.userTypes);
        switch(Integer.parseInt(strType, 16)){
            case 0:
                userType = userTypes[0];
                break;
            case 6:
                userType = userTypes[1];
                break;
            case 8:
                userType = userTypes[2];
                break;
            case 10:
                userType = userTypes[3];
                break;
            case 12:
                userType = userTypes[4];
                break;
            case 14:
                userType = userTypes[5];
                break;
            default:
                userType = "未定义";
                break;
        }
        return userType;
    }
}
