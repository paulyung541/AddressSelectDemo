package com.ysy.meituanaddressselect;

import android.app.Application;
import android.content.Context;

import com.ysy.meituanaddressselect.bean.CityList;


/**
 * Created by yang on 2016/7/28.
 */
public class BaseApp extends Application {
    public static Context context;

    public static CityList cityList;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();

        cityList = Utils.getCityList(this, "city.json");
        cityList.computePosition();//计算position
    }
}
