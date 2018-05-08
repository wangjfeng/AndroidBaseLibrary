package com.wangjf.library.network;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjf on 2018/3/21.
 */

public class NetWorkUtils {

    /**
     * 判断IP格式
     *
     * @param ip
     * @return
     */
    public static Boolean checkIPFormat(String ip){
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ip);
        if(matcher.matches()){
            return true;
        }else {
            return false;
        }
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "";
    }

    /**
     *
     * @Title getLocalMacAddress
     * @Description 获取MAC地址，设备启动后必须打开wifi一次后才能获取到mac地址
     * @author ZengXiaoJiang
     * @param context
     * @return
     * String
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        // 开机后打开过一次wifi，就会立刻得到MAC了
        if (info != null && info.getMacAddress() != null) {
            return info.getMacAddress();
        }

        return "";
    }

    public static String getMacByCmd() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    // 去空格
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     *
     * @Title checkWifiStatus
     * @Description TODO
     * @author ZengXiaoJiang
     * @param context
     * @return
     * boolean: true Wifi已打开   |false Wifi未打开
     */
    public static boolean checkWifiStatus(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            return true;
        }else {
            return false;
        }
    }
    /**
     *
     * @Title setWifi
     * @Description WIFI开关，必须增加“ACCESS_WIFI_STATE”和“CHANGE_WIFI_STATE”权限
     * @author ZengXiaoJiang
     * @param isEnable true：开启 false：关闭
     * @param context 上下文
     * @return
     * boolean: true 操作成功，false 操作失败
     */
    public static boolean setWifi(boolean isEnable, Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Log.i("setWifi", "wifi: " + wifiManager.isWifiEnabled());

        boolean ret = true;

        if (isEnable) {
            // 开启wifi
            if (!wifiManager.isWifiEnabled()) {
                ret = wifiManager.setWifiEnabled(true);
            }
        } else {// 关闭 wifi
            if (wifiManager.isWifiEnabled()) {
                ret = wifiManager.setWifiEnabled(false);
            }
        }

        return ret;
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress2(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        // 开机后打开过一次wifi，就会立刻得到MAC了
        if (info != null && info.getMacAddress() != null) {
            return info.getMacAddress();
        }

        // 如果得不到，一般就是wifi没开过，主动开一次，再关掉。
        boolean wifienable = wifi.isWifiEnabled();
        if (!wifienable) {
            wifi.setWifiEnabled(true);
        }
        // 等待结果，2-3秒看实际情况吧
        for (int i = 0; i < 10; i++) {
            info = wifi.getConnectionInfo();
            if (info != null && info.getMacAddress() != null) {
                // 原来是关闭的，那么记得关闭wifi
                wifi.setWifiEnabled(wifienable);
                return info.getMacAddress();
            }
            SystemClock.sleep(300);
        }

        return info.getMacAddress();
    }

    /**
     * 尝试打开wifi
     * @param manager
     * @return
     */
    private static boolean tryOpenWifi(WifiManager manager) {
        boolean softOpenWifi = false;
        int state = manager.getWifiState();
        if (state != WifiManager.WIFI_STATE_ENABLED && state != WifiManager.WIFI_STATE_ENABLING) {
            manager.setWifiEnabled(true);
            softOpenWifi = true;
        }

        return softOpenWifi;
    }

    /**
     * 尝试关闭MAC
     * @param manager
     */
    private static void tryCloseWifi(WifiManager manager) {
        manager.setWifiEnabled(false);
    }

    /**
     * 尝试获取MAC地址
     * @param manager
     * @return
     */
    private static String tryGetMAC(WifiManager manager) {
        WifiInfo wifiInfo = manager.getConnectionInfo();
        if (wifiInfo == null || wifiInfo.getMacAddress() == null || wifiInfo.getMacAddress().equals("")) {
            return null;
        }
        return wifiInfo.getMacAddress();
    }

    /**
     * 尝试读取MAC地址
     * @param internal
     * @param context
     * @return
     */
    public static String getMacFromDevice(int internal, Context context) {
        String mac = null;
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        mac = tryGetMAC(wifiManager);
        if (mac != null) {
            return mac;
        }
        // 获取失败，尝试打开wifi获取
        boolean isOkWifi = tryOpenWifi(wifiManager);
        for (int index = 0; index < internal; index++) {
            // 如果第一次没有成功，第二次做100毫秒的延迟。
            if (index != 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mac = tryGetMAC(wifiManager);
            if (mac != null) {
                break;
            }
        }
        // 尝试关闭wifi
        if (isOkWifi) {
            tryCloseWifi(wifiManager);
        }

        return mac;
    }
}
