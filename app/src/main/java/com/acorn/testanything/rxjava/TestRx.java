package com.acorn.testanything.rxjava;

import com.acorn.testanything.utils.LogUtilsKt;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by acorn on 2019/12/11.
 */
public class TestRx {

    public static void main(String[] args) {
        TestRx t = new TestRx();
        t.test3();
    }

    private void test() {
        String[] strs = new String[]{"1", "2", "3"};
        Observable.fromArray(strs)
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    private void test2() {
        Observable.just("123")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.parseInt(s);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void test3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("create:" + Thread.currentThread().getName());
                emitter.onNext("abc");
                emitter.onComplete();
            }
        })
                .subscribeOn(getNamedScheduler("after create"))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnSubscribe:" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(getNamedScheduler("after doOnSubscribe"))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("doOnSubscribe 2:" + Thread.currentThread().getName());
                    }
                })
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 获取指定名称线程
     *
     * @param name
     * @return
     */
    private Scheduler getNamedScheduler(final String name) {
        return Schedulers.from(Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, name);
            }
        }));
    }
}
