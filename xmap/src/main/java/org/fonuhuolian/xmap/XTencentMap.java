package org.fonuhuolian.xmap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * https://lbs.qq.com/uri_v1/index.html
 */
public class XTencentMap {

    /**
     * 是否安装腾讯地图
     */
    public static boolean isInstallTencentMap(Context context) {
        return XMapUtil.checkApkExist(context, "com.tencent.map");
    }

    public static Intent tencentPlanning(String endPointName, String endPointLat, String endPointLon, XTencentMode mode) {
        return tencentPlanning("", "", "", endPointName,
                endPointLat, endPointLon, mode);
    }

    // 获得高德地图路线规划的Intent
    public static Intent tencentPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                         String endPointName, String endPointLat, String endPointLon, XTencentMode mode) {

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
            buffer.append(startingPointLat);
            buffer.append(",");
            buffer.append(startingPointLon);
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

        buffer.append(endPointLat);
        buffer.append(",");
        buffer.append(endPointLon);

        buffer.append("&referer=");

        buffer.append("OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77");

        Intent i1 = new Intent();
        i1.setData(Uri.parse(buffer.toString()));


        return i1;
    }
}
