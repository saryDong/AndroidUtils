package com.abu.utilsmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 说明: 倒计时类，某个Activity超过20秒未有操作强制销毁
 * 作者: 董长峰
 * 添加时间: 2019-8-15 14:32
 * 修改时间: 2019-8-15 14:32
 **/

public class TimerUtil {
    private int tmel = 60;
    private Activity activity;
    private Timer timer;
    private static volatile TimerUtil timerUtil = null;
    private boolean isEnd = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (desTime != null) {
                        desTime.onDesTime(tmel);
                    }
                    if (tmel == 0) {
                        if (timer != null) {
                            timer.cancel();
                            timer.purge();
                            timer = null;
                        }
                        if (timerUtil != null) {
                            timerUtil = null;
                        }

                        if (activity!=null){
                            /*Intent intent = new Intent();
                            intent.setClass(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                            activity = null;*/
                            //倒计时结束
                        }
                    } else {
                        tmel--;
                    }
                    break;
            }
        }
    };

    private DesTime desTime;

    public void setDesTime(DesTime desTime) {
        this.desTime = desTime;
    }

    public interface DesTime {
        void onDesTime(int time);
    }

    private TimerUtil(Context context) {
        this.activity = (Activity) context;
    }

    public static TimerUtil getInstance(Context context) {
        /*if (timerUtil == null) {
            synchronized (TimerUtil.class) {
                if (timerUtil == null) {*/
                    timerUtil = new TimerUtil(context);
           /*     }
            }
        }*/
        return timerUtil;
    }

    public void setTime(int time) {
        this.tmel = time;
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
            isEnd = false;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isEnd) {
                    handler.sendEmptyMessage(1);
                }
            }
        }, 500, 1000);
    }

    public void cancle() {
        tmel = 20;
        isEnd = true;
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
          /*  if (activity!=null){
                activity=null;
            }*/
        }
        if (timerUtil != null) {
            timerUtil = null;
        }
        handler.removeMessages(1);
    }

}
