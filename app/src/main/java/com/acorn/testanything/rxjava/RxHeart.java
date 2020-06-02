package com.acorn.testanything.rxjava;

import android.util.Log;

import com.acorn.testanything.testWithOutput.IOutput;
import com.acorn.testanything.testWithOutput.ITest;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 轮询模式
 * Created by acorn on 2020/5/27.
 */
public class RxHeart implements ITest {

    private Observable<Integer> getDataFromServer() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (emitter.isDisposed()) {
                    return;
                }
                int randomSleep = new Random().nextInt(5);
                try {
                    Thread.sleep(randomSleep * 1000);
                } catch (Exception e) {
                }
                if (emitter.isDisposed()) {
                    return;
                }
                if (randomSleep % 2 == 0) {
                    emitter.onError(new Exception("get fake error for " + randomSleep));
                    return;
                }
                emitter.onNext(randomSleep);
                emitter.onComplete();
            }
        });
    }

    // 按照顺序loop，意味着第一次结果请求完成后，再考虑下次请求
    @Override
    public void test(@NotNull IOutput output) {

        Disposable disposable = getDataFromServer()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        output.outputByThread("loopSequence subscribe");
                    }
                })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        output.outputByThread("loopSequence doOnNext: " + integer);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        output.outputByThread("loopSequence doOnError: " + throwable.getMessage());
                    }
                })
                .delay(1, TimeUnit.SECONDS, true)       // 设置delayError为true，表示出现错误的时候也需要延迟5s进行通知，达到无论是请求正常还是请求失败，都是5s后重新订阅，即重新请求。
                .subscribeOn(Schedulers.io())
                .repeat(3)   // repeat保证请求成功后能够重新订阅。
                .retry(3)    // retry保证请求失败后能重新订阅
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        output.output("onNext:" + integer + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        output.output("onError:" + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        output.output("onComplete");
                    }
                });
    }
}
