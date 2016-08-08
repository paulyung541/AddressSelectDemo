package com.ysy.meituanaddressselect;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.ysy.meituanaddressselect.bean.CityList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by yang on 2016/7/28.
 */
public class Utils {
    //dp 转 px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //px 转 dp
    public static int pxToDp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 显示 Toast
     * */
    public static void showToast(String s) {
        Toast.makeText(BaseApp.context, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 解析 assets 里面的 json 文件，获取实体对象
     * 调用第三方：fastjson
     * */
    public static CityList getCityList(Context context, String fileName) {
        CityList res = null;
        try {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = "";
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            res = JSONObject.parseObject(result, CityList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
