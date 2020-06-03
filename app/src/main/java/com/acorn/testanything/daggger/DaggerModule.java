package com.acorn.testanything.daggger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by acorn on 2020/6/2.
 */
@Module
public class DaggerModule {
    @Named("a")
    @Provides
    BeanA provideBeanA() {
        return new BeanA("aaaaa");
    }

    @Named("b")
    @Provides
    BeanA provideBeanA2() {
        return new BeanA("bbbbbbb");
    }

    @Named("c")
    @Provides
    BeanA provideBeanA3(String str) {
        return new BeanA(str);
    }

    @Named("d")
    @Provides
    BeanA provideBeanA4(Integer i) {
        return new BeanA(String.valueOf(i));
    }

    @Provides
    String provideStr() {
        return "cccc";
    }

    @Provides
    Integer provideInt() {
        return 4;
    }
}
