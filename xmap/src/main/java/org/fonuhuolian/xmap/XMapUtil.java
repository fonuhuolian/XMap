package org.fonuhuolian.xmap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import org.fonuhuolian.xmap.base.XAMap;
import org.fonuhuolian.xmap.base.XBaiduMap;
import org.fonuhuolian.xmap.base.XTencentMap;

import java.text.DecimalFormat;

public class XMapUtil {


    /**
     * 出行规划
     */
    public static Intent xMapPlanningByAutoLocation(String endPointName, String endPointLat, String endPointLon, XMapMode map, XMapTravelMode mode) {

        Intent intent = null;

        switch (map) {

            case XAMAP:
                intent = XAMap.amapPlanning(endPointName, endPointLat, endPointLon, mode);
                break;
            case XBAIDU:
                intent = XBaiduMap.baiDuRoutePlanning(endPointName, endPointLat, endPointLon, mode);
                break;
            case XTENCENT:
                intent = XTencentMap.tencentPlanning(endPointName, endPointLat, endPointLon, mode);
                break;

        }

        return intent;
    }

    public static Intent xMapPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                      String endPointName, String endPointLat, String endPointLon, XMapMode map, XMapTravelMode mode) {
        Intent intent = null;

        switch (map) {

            case XAMAP:
                intent = XAMap.amapPlanning(startingPointName, startingPointLat, startingPointLon, endPointName, endPointLat, endPointLon, mode);
                break;
            case XBAIDU:
                intent = XBaiduMap.baiDuRoutePlanning(startingPointName, startingPointLat, startingPointLon, endPointName, endPointLat, endPointLon, mode);
                break;
            case XTENCENT:
                intent = XTencentMap.tencentPlanning(startingPointName, startingPointLat, startingPointLon, endPointName, endPointLat, endPointLon, mode);
                break;

        }

        return intent;
    }

    /**
     * distance为m(并根据数值自动转换为m或km)
     */
    public static String getDistance(double lon1, double lat1, double lon2, double lat2) {
        return MapUtils.getDistance(lon1, lat1, lon2, lat2);
    }


    /**
     * 判断是否安装某地图应用
     */
    public static boolean checkMapExist(Context context, XMapMode map) {

        String packageName = "";

        switch (map) {

            case XAMAP:
                packageName = "com.autonavi.minimap";
                break;
            case XBAIDU:
                packageName = "com.baidu.BaiduMap";
                break;
            case XTENCENT:
                packageName = "com.tencent.map";
                break;

        }

        return MapUtils.checkApkExist(context, packageName);
    }


    /**
     * 私有类 不对外部提供访问
     */
    private static class MapUtils {

        private static double DEF_PI180 = 0.01745329252; // PI/180.0
        private static double DEF_R = 6370693.5; // radius of earth

        private static boolean checkApkExist(Context context, String packageName) {

            if (TextUtils.isEmpty(packageName))
                return false;

            try {
                context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }


        private static String getDistance(double lon1, double lat1, double lon2, double lat2) {
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

        private static String trans(double distance) {
            boolean isBig = false; // 是否为大于等于1000m
            if (distance >= 1000) {
                distance /= 1000;
                isBig = true;
            }
            return (new DecimalFormat(".00").format(distance)) + (isBig ? "km" : "m");
        }
    }

}
