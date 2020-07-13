package com.acorn.testanything.countdown;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.acorn.testanything.utils.LogUtilKt;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CountDownTimerService extends Service {
    public static final String TOTAL_SEC = "totalSec";
    public static final String COUNT_DOWN_MILL_STEP = "countDownMillStep";
    private CountDownTimer countDownTimer;
    private Disposable disposable;
    private int totalSec;
    private long countDownMillStep;

    public CountDownTimerService() {
    }


    /**
     * 倒计时的回调接口
     */
    private OnCountDownTimerListener onCountDownTimerListener;

    /**
     * 注册回调接口的方法，供外部调用
     *
     * @param onCountDownTimerListener
     */
    public void setOnCountDownTimerListener(OnCountDownTimerListener onCountDownTimerListener) {
        this.onCountDownTimerListener = onCountDownTimerListener;
    }


    public void startCountDownTimer(int totalSec, final long countDownMillStep) {
        countDownTimer = new CountDownTimer(totalSec * 1000L, countDownMillStep) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (onCountDownTimerListener != null) {
                    int sec = totalSec - (int) ((millisUntilFinished + 500L) / 1000L);
                    int sec2 = (int) ((millisUntilFinished + 500L) / 1000L);
                    double sec3 = Math.round((double) millisUntilFinished / 1000);
                    LogUtilKt.logI("millSec:" + millisUntilFinished + ",sec2:" + sec2 + ",sec3:" + sec3);
                    onCountDownTimerListener.onTick1(sec);
                }
            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();

        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (onCountDownTimerListener != null)
                        onCountDownTimerListener.onTick2(aLong.intValue());
                });
    }

    /**
     * 返回一个Binder对象
     */
    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null) {
            totalSec = intent.getIntExtra(TOTAL_SEC, -1);
            countDownMillStep = intent.getLongExtra(COUNT_DOWN_MILL_STEP, -1);
            if (totalSec != -1 && countDownMillStep != -1) {
                startCountDownTimer(totalSec, countDownMillStep);
            }
        }
        return new MsgBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != disposable)
            disposable.dispose();
        return super.onUnbind(intent);
    }

    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public CountDownTimerService getService() {
            return CountDownTimerService.this;
        }
    }

    public interface OnCountDownTimerListener {
        void onTick1(int sec);

        void onTick2(int sec);
    }
}
