package com.acorn.testanything.daggger;

import dagger.Component;

/**
 * Created by acorn on 2020/6/2.
 */
@Component(modules = {DaggerModule.class})
public interface DaggerComponent {
    void inject(TestDagger testDagger);
}
