package org.fonuhuolian.xmap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.fonuhuolian.xmap.base.XAMap;
import org.fonuhuolian.xmap.base.XBaiduMap;
import org.fonuhuolian.xmap.base.XTencentMap;
import org.fonuhuolian.xmap.gps.XGPS;
import org.fonuhuolian.xmap.gps.XGPSConverterUtils;

import java.text.DecimalFormat;

public class XMapUtil {


    /**
     * 出行规划
     */
    public static Intent xMapPlanningByAutoLocation(String endPointName, String endPointLat, String endPointLon, XMapMode map, @NonNull XMapTravelMode mode, @NonNull XCoordinateSystem system) {

        Intent intent = null;

        switch (map) {

            case XAMAP:
                intent = XAMap.amapPlanning(endPointName, endPointLat, endPointLon, mode, system);
                break;
            case XBAIDU:
                intent = XBaiduMap.baiDuRoutePlanning(endPointName, endPointLat, endPointLon, mode, system);
                break;
            case XTENCENT:
                intent = XTencentMap.tencentPlanning(endPointName, endPointLat, endPointLon, mode, system);
                break;

        }

        return intent;
    }

    public static Intent xMapPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                      String endPointName, String endPointLat, String endPointLon, XMapMode map, @NonNull XMapTravelMode mode, @NonNull XCoordinateSystem system) {
        Intent intent = null;

        switch (map) {

            case XAMAP:
                intent = XAMap.amapPlanning(startingPointName, startingPointLat, startingPointLon, endPointName, endPointLat, endPointLon, mode, system);
                break;
            case XBAIDU:
                intent = XBaiduMap.baiDuRoutePlanning(startingPointName, startingPointLat, startingPointLon, endPointName, endPointLat, endPointLon, mode, system);
                break;
            case XTENCENT:
                intent = XTencentMap.tencentPlanning(startingPointName, startingPointLat, startingPointLon, endPointName, endPointLat, endPointLon, mode, system);
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
     * 返回正确的坐标
     *
     * @param xGps      要转换的GPS
     * @param gpsSystem 此gps所属坐标系
     * @param finalMap  目的地图
     * @return
     */
    public static XGPS covertGPS(XGPS xGps, XCoordinateSystem gpsSystem, XMapMode finalMap) {

        XGPS converXGPS = null;

        if (gpsSystem == XCoordinateSystem.BAIDU_SYSETEM) {
            // 当前的坐标系为百度坐标系
            switch (finalMap) {
                case XBAIDU:
                    // 无需转换
                    converXGPS = xGps;
                    break;
                case XAMAP:
                case XTENCENT:
                    converXGPS = XGPSConverterUtils.bd09_To_Gcj02(xGps.getLat(), xGps.getLon());
                    break;
            }

        } else if (gpsSystem == XCoordinateSystem.AMAP_TENCENT_MARS_SYSTEM) {
            // 当前的坐标系为高德、腾讯的火星坐标系
            switch (finalMap) {
                case XBAIDU:
                    converXGPS = XGPSConverterUtils.gcj02_To_Bd09(xGps.getLat(), xGps.getLon());
                    break;
                case XAMAP:
                    // 无需转换
                    converXGPS = xGps;
                    break;
                case XTENCENT:
                    // 无需转换
                    converXGPS = xGps;
                    break;
            }
        }

        return converXGPS;
    }

    public static XGPS covertGPS(String xGps, XCoordinateSystem gpsSystem, XMapMode finalMap) {

        String[] split = xGps.split(",", -1);

        if (split.length != 2) {
            throw new RuntimeException("GPS格式为：lat,lon");
        }

        return covertGPS(new XGPS(Double.parseDouble(split[0]), Double.parseDouble(split[1])), gpsSystem, finalMap);
    }

    public static double covertGPSLat(XGPS xGps, XCoordinateSystem gpsSystem, XMapMode finalMap) {

        return covertGPS(xGps, gpsSystem, finalMap).getLat();
    }

    public static double covertGPSLat(String xGps, XCoordinateSystem gpsSystem, XMapMode finalMap) {

        String[] split = xGps.split(",", -1);

        if (split.length != 2) {
            throw new RuntimeException("GPS格式为：lat,lon");
        }

        return covertGPS(new XGPS(Double.parseDouble(split[0]), Double.parseDouble(split[1])), gpsSystem, finalMap).getLat();
    }

    public static double covertGPSLon(XGPS xGps, XCoordinateSystem gpsSystem, XMapMode finalMap) {

        return covertGPS(xGps, gpsSystem, finalMap).getLon();
    }

    public static double covertGPSLon(String xGps, XCoordinateSystem gpsSystem, XMapMode finalMap) {

        String[] split = xGps.split(",", -1);

        if (split.length != 2) {
            throw new RuntimeException("GPS格式为：lat,lon");
        }

        return covertGPS(new XGPS(Double.parseDouble(split[0]), Double.parseDouble(split[1])), gpsSystem, finalMap).getLon();
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
