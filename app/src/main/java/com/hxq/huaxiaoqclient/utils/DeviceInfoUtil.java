package com.hxq.huaxiaoqclient.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import org.senydevpkg.utils.ALog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.UUID;

import static com.alibaba.mtl.log.model.LogField.IMEI;

/**
 * @ClassName DeviceInfoUtil
 * @Description 获取DeviceInfo工具类
 */
public class DeviceInfoUtil {

    private static final String TAG = "DeviceInfoUtil";
    private static Context mContext;

    /**
     * 获取DeviceInfo对象
     *
     * @param context 要获取对象的环境
     * @return DeviceInfo对象
     */
    public static String getDeviceInfo(Context context) {

        mContext = context;
        String deviceID = "";
        try {
            // 获取DeviceID
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String IMEI = telephonyManager.getDeviceId();
            String mac = getLocalMacAddress();
            if (!StringUtils.isEmpty(IMEI) && !IMEI.startsWith("000000000")) {
                deviceID = IMEI;
            } else if (!StringUtils.isEmpty(mac)) {
                deviceID = MD5(mac);
            } else {
                deviceID = id(context);
            }
            ALog.e("deviceID is " + deviceID + " imem " + IMEI + " mac" + mac + " md5mac " + MD5(mac));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceID;
    }
    public static String getIdInfo(Context context) {

        mContext = context;
        String deviceID = "";
        try {
            String mac = getLocalMacAddress();
            if (!StringUtils.isEmpty(mac)) {
                deviceID = MD5(mac);
            } else {
                deviceID = id(context);
            }
            ALog.e("deviceID is " + deviceID + " imem " + IMEI + " mac" + mac + " md5mac " + MD5(mac));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceID;
    }
    /**
     * 获取MAC地址
     *
     * @return
     */
    public static String getLocalMacAddress() {
        try {
            WifiManager wifi = (WifiManager) mContext
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation)
            throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();

        return new String(bytes);
    }

    private static void writeInstallationFile(File installation)
            throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}