package org.fonuhuolian.xmap;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class XMapUtil {

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
