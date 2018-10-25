package org.fonuhuolian.xmap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * https://lbs.amap.com/api/amap-mobile/summary
 */
public class XAMap {

    /**
     * 是否安装高德地图
     */
    public static boolean isInstallAMap(Context context) {
        return XMapUtil.checkApkExist(context, "com.autonavi.minimap");
    }

    public static Intent amapPlanning(String endPointName, String endPointLat, String endPointLon, XAMapMode mode) {
        return amapPlanning("", "", "",
                endPointName, endPointLat, endPointLon, mode);
    }

    // 获得高德地图路线规划的Intent
    public static Intent amapPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                      String endPointName, String endPointLat, String endPointLon, XAMapMode mode) {

        StringBuffer buffer = new StringBuffer("amapuri://route/plan/?");

        if (!TextUtils.isEmpty(startingPointLat) && !TextUtils.isEmpty(startingPointLon)) {
            buffer.append("slat=");
            buffer.append(startingPointLat);
            buffer.append("&slon=");
            buffer.append(startingPointLon);
            buffer.append("&");
        }

        if (!TextUtils.isEmpty(startingPointName)) {

            buffer.append("sname=");
            buffer.append(startingPointName);
            buffer.append("&");
        }


        if (TextUtils.isEmpty(endPointLat) || TextUtils.isEmpty(endPointLon)) {
            throw new RuntimeException("终点的经纬度不能为空");
        }

        buffer.append("dlat=");
        buffer.append(endPointLat);
        buffer.append("&dlon=");
        buffer.append(endPointLon);
        buffer.append("&");

        if (!TextUtils.isEmpty(endPointName)) {
            buffer.append("dname=");
            buffer.append(endPointName);
            buffer.append("&");
        }

        buffer.append("dev=0&t=");
        buffer.append(mode.getMode());

        Intent i1 = new Intent();
        i1.setAction(Intent.ACTION_VIEW);
        i1.addCategory(Intent.CATEGORY_DEFAULT);
        i1.setPackage("com.autonavi.minimap");
        i1.setData(Uri.parse(buffer.toString()));

        return i1;
    }
}
