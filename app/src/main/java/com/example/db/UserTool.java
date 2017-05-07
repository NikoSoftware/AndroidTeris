package com.example.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.constant.Constant;

/**
 * Created by niko on 2017/5/7.
 */

public class UserTool {

    /**
     * 储存账号
     */
    public static void saveAccount(Context context,String name,String passwords){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cname",name);
        edit.putString("cpassword",passwords);
        edit.commit();

    }

    /**
     * 获取账号
     */
    public static UserInfo getAccount(Context context){
        UserInfo userInfo = new UserInfo();
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        userInfo.setName(sp.getString("cname",""));
        userInfo.setPasswords(sp.getString("cpassword",""));

        if(userInfo.getName().equals("")||userInfo.getPasswords().equals("")){
            Constant.CustomName=null;
            Constant.PassWords=null;
            return null;
        }else{
            Constant.CustomName = userInfo.getName();
            Constant.PassWords = userInfo.getPasswords();
        }
        return userInfo;
    }



}
