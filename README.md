# XMap
地图工具

> 添加依赖

`root build.gradle `
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
`module build.gradle `
```
 implementation 'com.github.fonuhuolian:XMap:1.0.7'
```

> 混淆
```
-dontwarn org.fonuhuolian.xmap.**
-keep class org.fonuhuolian.xmap.**{*;}
```

> 代码

```

// 是否安装了地图
XMapUtil.checkMapExist(Context context, XMapMode map);
// 线路规划
XMapUtil.xMapPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                                                           String endPointName, String endPointLat, String endPointLon, XMapMode map, XMapTravelMode mode);
XMapUtil.xMapPlanningByAutoLocation(String endPointName, String endPointLat, String endPointLon, XMapMode map, XMapTravelMode mode);
// 测量两点之间的距离（返回值小于1000 则为xx m 如果大于1000则为xx km）
XMapUtil.getDistance(double lon1, double lat1, double lon2, double lat2);
```

> 效果

![效果](https://github.com/fonuhuolian/XMap/blob/master/screenshot/a.png?raw=true)
![效果](https://github.com/fonuhuolian/XMap/blob/master/screenshot/b.png?raw=true)