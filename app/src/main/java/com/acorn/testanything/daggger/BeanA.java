package com.acorn.testanything.daggger;

import javax.inject.Inject;

import dagger.Provides;

/**
 * Created by acorn on 2020/6/2.
 */
public class BeanA {
    public String aa;

    public BeanA(String abc) {
        aa = abc;
    }
}
