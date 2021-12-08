package com.sz.myapplication;

import android.app.Activity;

/**
 * author：created by renlei on 2021/11/25
 * eMail :renlei@yitong.com.cn
 */
public class APT_RLButterKnife {

    public static void bind(Activity activity) {
        String activityName = activity.getClass().getName() + "_ViewBind";
        try {
            Class<?> aClass = Class.forName(activityName);
            APT_IBinder iBinder = (APT_IBinder) aClass.newInstance();
            iBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
