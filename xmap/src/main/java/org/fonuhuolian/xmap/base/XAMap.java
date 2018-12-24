package org.fonuhuolian.xmap.base;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.fonuhuolian.xmap.XCoordinateSystem;
import org.fonuhuolian.xmap.XMapMode;
import org.fonuhuolian.xmap.XMapTravelMode;
import org.fonuhuolian.xmap.XMapUtil;
import org.fonuhuolian.xmap.gps.XGPS;

/**
 * https://lbs.amap.com/api/amap-mobile/summary
 */
public class XAMap {


    public static Intent amapPlanning(String endPointName, String endPointLat, String endPointLon, @NonNull XMapTravelMode mode, @NonNull XCoordinateSystem system) {
        return amapPlanning("", "", "",
                endPointName, endPointLat, endPointLon, mode, system);
    }

    // 获得高德地图路线规划的Intent
    public static Intent amapPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                      String endPointName, String endPointLat, String endPointLon, @NonNull XMapTravelMode mode, @NonNull XCoordinateSystem system) {

        if (!mode.getMapName().equals("高德")) {
            throw new RuntimeException("选择的出行模式与调用的地图不符");
        }

        StringBuffer buffer = new StringBuffer("amapuri://route/plan/?");

        if (!TextUtils.isEmpty(startingPointLat) && !TextUtils.isEmpty(startingPointLon)) {
            buffer.append("slat=");
            buffer.append(XMapUtil.covertGPSLat(new XGPS(Double.parseDouble(startingPointLat), Double.parseDouble(startingPointLon)), system, XMapMode.XAMAP));
            buffer.append("&slon=");
            buffer.append(XMapUtil.covertGPSLon(new XGPS(Double.parseDouble(startingPointLat), Double.parseDouble(startingPointLon)), system, XMapMode.XAMAP));
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
        buffer.append(XMapUtil.covertGPSLat(new XGPS(Double.parseDouble(endPointLat), Double.parseDouble(endPointLon)), system, XMapMode.XAMAP));
        buffer.append("&dlon=");
        buffer.append(XMapUtil.covertGPSLon(new XGPS(Double.parseDouble(endPointLat), Double.parseDouble(endPointLon)), system, XMapMode.XAMAP));
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
