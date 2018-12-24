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
 * https://lbs.qq.com/uri_v1/index.html
 */
public class XTencentMap {


    public static Intent tencentPlanning(String endPointName, String endPointLat, String endPointLon, @NonNull XMapTravelMode mode, @NonNull XCoordinateSystem system) {
        return tencentPlanning("", "", "", endPointName,
                endPointLat, endPointLon, mode, system);
    }

    // 获得高德地图路线规划的Intent
    public static Intent tencentPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                         String endPointName, String endPointLat, String endPointLon, @NonNull XMapTravelMode mode, @NonNull XCoordinateSystem system) {


        if (!mode.getMapName().equals("腾讯")) {
            throw new RuntimeException("选择的出行模式与调用的地图不符");
        }

        StringBuffer buffer = new StringBuffer("qqmap://map/routeplan?");

        buffer.append("type=");
        buffer.append(mode.getMode());
        buffer.append("&");

        if (!TextUtils.isEmpty(startingPointName)) {
            buffer.append("from=");
            buffer.append(startingPointName);
            buffer.append("&");
        }

        if (!TextUtils.isEmpty(startingPointLat) && !TextUtils.isEmpty(startingPointLon)) {
            buffer.append("fromcoord=");
            buffer.append(XMapUtil.covertGPSLat(new XGPS(Double.parseDouble(startingPointLat), Double.parseDouble(startingPointLon)), system, XMapMode.XTENCENT));
            buffer.append(",");
            buffer.append(XMapUtil.covertGPSLon(new XGPS(Double.parseDouble(startingPointLat), Double.parseDouble(startingPointLon)), system, XMapMode.XTENCENT));
        } else {
            buffer.append("fromcoord=CurrentLocation");
        }

        buffer.append("&");

        if (!TextUtils.isEmpty(endPointName)) {
            buffer.append("to=");
            buffer.append(endPointName);
            buffer.append("&");
        }

        buffer.append("tocoord=");

        if (TextUtils.isEmpty(endPointLat) || TextUtils.isEmpty(endPointLon)) {
            throw new RuntimeException("终点的经纬度不能为空");
        }

        buffer.append(XMapUtil.covertGPSLat(new XGPS(Double.parseDouble(endPointLat), Double.parseDouble(endPointLon)), system, XMapMode.XTENCENT));
        buffer.append(",");
        buffer.append(XMapUtil.covertGPSLon(new XGPS(Double.parseDouble(endPointLat), Double.parseDouble(endPointLon)), system, XMapMode.XTENCENT));

        buffer.append("&referer=");

        buffer.append("OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77");

        Intent i1 = new Intent();
        i1.setData(Uri.parse(buffer.toString()));


        return i1;
    }
}
