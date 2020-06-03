package com.acorn.testanything.daggger;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by acorn on 2020/6/2.
 */
public class TestDagger {
    @Named("a")
    @Inject
    BeanA a;

    @Named("b")
    @Inject
    BeanA b;

    @Named("c")
    @Inject
    BeanA c;

    @Named("d")
    @Inject
    BeanA d;

    public static void main(String[] args) {
        TestDagger test = new TestDagger();
        test.print();
    }

    public TestDagger() {
        DaggerDaggerComponent.builder().build().inject(this);
    }

    public void print() {
        System.out.println(a.aa);
        System.out.println(b.aa);
        System.out.println(c.aa);
        System.out.println(d.aa);
    }
}
