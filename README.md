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
 implementation 'com.github.fonuhuolian:XMap:1.0.2'
```

> 混淆
```
-dontwarn org.fonuhuolian.xmap.**
-keep class org.fonuhuolian.xmap.**{*;}
```

> 代码

```

// XBaiduMap(百度地图相关)、XAMap(高德地图相关)、XTencentMap(腾讯地图相关)
// 是否安装了地图
isInstallBaiDuMap(Context context);
// 线路规划
amapPlanning(String startingPointName, String startingPointLat, String startingPointLon,
                String endPointName, String endPointLat, String endPointLon, XAMapMode mode)
```

> 效果

![效果](https://github.com/fonuhuolian/XMap/blob/master/screenshot/a.jpg?raw=true)  ![效果](https://github.com/fonuhuolian/XMap/blob/master/screenshot/a.jpg?raw=true)