package com.example.androidteris;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.Tool.Util;

/**
 * Created by niko on 2017/5/7.
 */

public class BaseActivity extends Activity {

    private BackService mBackService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,BackService.class);
        bindService(intent, service, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mBackService!=null){
            mBackService.startBackMusic();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        /**
         * 判断程序是不是在后台
         */
        if(Util.isBackground(this)){
            if(mBackService!=null) {
                mBackService.pauseBackMusic();
            }
        }

    }

    private ServiceConnection service  =new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub

            BackService.LocalBinder binder =  (BackService.LocalBinder)service;
            mBackService = binder.getService();
            mBackService.CreateBackSound();
            Log.d("TAg===>","backPlay ok");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(service);
    }
}
