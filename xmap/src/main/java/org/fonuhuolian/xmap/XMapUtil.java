package org.fonuhuolian.xmap;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.text.DecimalFormat;

public class XMapUtil {


    private double DEF_PI = 3.14159265359; // PI
    private double DEF_2PI = 6.28318530712; // 2*PI
    private double DEF_PI180 = 0.01745329252; // PI/180.0
    private double DEF_R = 6370693.5; // radius of earth

    /**
     * 返回为m，适合短距离测量
     */
    public String getShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return trans(distance);
    }

    /**
     * 返回为m,适合长距离测量
     */
    public String getLongDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2)
                * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return trans(distance);
    }

    private String trans(double distance) {
        boolean isBig = false; // 是否为大于等于1000m
        if (distance >= 1000) {
            distance /= 1000;
            isBig = true;
        }
        return (new DecimalFormat(".00").format(distance)) + (isBig ? "千米" : "米");
    }


    /**
     * 判断是否安装某地图应用
     */
    public static boolean checkApkExist(Context context, String packageName) {

        if (TextUtils.isEmpty(packageName))
            return false;

        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
