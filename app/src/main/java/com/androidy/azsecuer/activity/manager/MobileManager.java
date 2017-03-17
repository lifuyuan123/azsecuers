package com.androidy.azsecuer.activity.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import com.androidy.azsecuer.activity.entity.Mobilchild;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/16.
 */
public class MobileManager {
    private Context context;
    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private PackageManager packageManager;

    public MobileManager(Context context) {
        this.context = context;
        packageManager = context.getPackageManager();
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    // 是否同意指定权限(Manifest.permission.ACCESS_NETWORK_STATE)
    public boolean isGranterPermission(String permission) {
        int result = packageManager.checkPermission(permission, context.getPackageName());
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    // 内存信息 -------------------------------------------------
    public ArrayList<Mobilchild> getMemoryMessage(Context context) {
        ArrayList<Mobilchild> childs = new ArrayList<Mobilchild>();
        String maxRam = PublicUtils.formatSize(MemoryManager.getTotalRamMemory());
        String freeRam = PublicUtils.formatSize(MemoryManager.getAvailRamMemory(context));
        String inRom = PublicUtils.formatSize(MemoryManager.getTotalInRom(context));
        String outRom = PublicUtils.formatSize(MemoryManager.getTotalOutRom(context));
        childs.add(new Mobilchild("最大RAM:", maxRam));
        childs.add(new Mobilchild("空闲RAM:", freeRam));
        childs.add(new Mobilchild("内置空间:", inRom));
        childs.add(new Mobilchild("外置空间:", outRom));
        return childs;
    }

    // 设备信息 -------------------------------------------------
    public ArrayList<Mobilchild> getPhoneMessage() {
        ArrayList<Mobilchild> childs = new ArrayList<Mobilchild>();
        childs.add(new Mobilchild("手机品牌:", getPhoneName1()));
        childs.add(new Mobilchild("手机制造商:", getPhoneName2()));
        childs.add(new Mobilchild("手机型号:", getPhoneModelName()));
        childs.add(new Mobilchild("手机分辨率:", getPhoneResolution()));
        return childs;
    }
    /** 设备品牌 */
    public String getPhoneName1() {
        return Build.BRAND.toUpperCase();
    }

    /** 设备制造商 */
    public String getPhoneName2() {
        return Build.MANUFACTURER;
    }

    /** 设备型号名称(xt910) */
    public String getPhoneModelName() {
        // 带国家用 PRODUCT
        return Build.MODEL.toUpperCase();
    }

    /** 获取手机分辨率 */
    public String getPhoneResolution() {
        String resolution = "";
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        resolution = metrics.widthPixels + "*" + metrics.heightPixels;
        return resolution;
    }

    // 系统信息 -------------------------------------------------
    public ArrayList<Mobilchild> getSystemMessage() {
        ArrayList<Mobilchild> childs = new ArrayList<Mobilchild>();
        childs.add(new Mobilchild("系统版本:", getSystemVersion()));
        childs.add(new Mobilchild("基带版本:", getSystemBasebandVersion()));
        return childs;
    }

    /** 设备系统版本号 (4.1.2?) */
    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /** 设备系统基带版本 */
    public String getSystemBasebandVersion() {
        return Build.RADIO;
    }

    // 网络信息 -------------------------------------------------
    public ArrayList<Mobilchild> getWIFIMessage() {
        ArrayList<Mobilchild> childs = new ArrayList<Mobilchild>();
        childs.add(new Mobilchild("网络类型:", getNetworkType()));
        childs.add(new Mobilchild("WIFI名称:", getWifiName()));
        childs.add(new Mobilchild("WIFI-IP:", getWifiIP()));
        childs.add(new Mobilchild("WIFI速度:", getWifiSpeed()));
        return childs;
    }

    /** 设备网络连接类型 (OFFLINE ? WIFI ? MOBILE) permission.ACCESS_NETWORK_STATE */
    public String getNetworkType() {
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return "OFFLINE";
        }
        return info.getTypeName();
    }

    /** 设备Wifi名称 */
    public String getWifiName() {
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getSSID() + "";
    }

    /** 设备Wifi的IP */
    public String getWifiIP() {
        WifiInfo info = wifiManager.getConnectionInfo();
        long ip = info.getIpAddress();
        return longToIP(ip);
    }

    /** 设备Wifi的速度 */
    public String getWifiSpeed() {
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getLinkSpeed() + "";
    }

    private String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        sb.append(".");
        // 将高1位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        return sb.toString();
    }

    // 相机信息 -------------------------------------------------
    public ArrayList<Mobilchild> getCameraMessage() {
        ArrayList<Mobilchild> childs = new ArrayList<Mobilchild>();
        childs.add(new Mobilchild("相机像素:", getCameraResolution()));
        childs.add(new Mobilchild("闪光灯状态:", getCameraFlashMode()));
        return childs;
    }

    /** 获取相机像素 */
    public String getCameraResolution() {
        String cameraResolution = "";
        try {
            // Camera camera = Camera.open();
            // Camera.Parameters parameters = camera.getParameters();
            // List<Size> sizes = parameters.getSupportedPictureSizes();
            // Size size = null;
            // for (Size s : sizes) {
            // if (size == null) {
            // size = s;
            // } else if (size.height * s.width < s.height * s.width) {
            // size = s;
            // }
            // }
            // cameraResolution = (size.width * size.height) / 10000 + "万像素";
            // camera.release();
        } catch (Exception e) {
            return "访问出错";
        }
        return cameraResolution;
    }

    /** 获取闪光灯状态 */
    public String getCameraFlashMode() {
        String flashMode = "";
        try {
            // Camera camera = Camera.open();
            // Camera.Parameters parameters = camera.getParameters();
            // flashMode = parameters.getFlashMode();
            // if (flashMode.equals(Parameters.FLASH_MODE_AUTO)) {
            // flashMode = "自动模式";
            // }
            // if (flashMode.equals(Parameters.FLASH_MODE_OFF)) {
            // flashMode = "关闭模式";
            // }
            // if (flashMode.equals(Parameters.FLASH_MODE_ON)) {
            // flashMode = "打开模式";
            // }
            // camera.release();
            // flashMode = "其它模式";
        } catch (Exception e) {
            flashMode = "访问出错";
            return flashMode;
        }
        return flashMode;
    }
}
