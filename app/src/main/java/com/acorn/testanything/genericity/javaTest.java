package com.acorn.testanything.genericity;

import com.acorn.testanything.kotlin.Data;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class javaTest {
    public static void main(String[] args) {
//        testExtends(new ArrayList<Animal>());
//        testExtends(new ArrayList<Cat>());
//        testSuper(new ArrayList<Animal>());
        //error!!
        //testSuper(new ArrayList<Cat>());

//        test();

        Data data = new Data("方法", 111);
        testFunParam(data);
        System.out.println("data out fun :" + data);
    }

    private static void testExtends(List<? extends Animal> list) {
        //error!!
        //list.add(new Cat());
        //error!!
        //list.add(new Animal());
    }

    private static void testSuper(List<? super Animal> list) {
        list.add(new Animal());
        list.add(new Cat());
    }

    private static void test() {
        ReferenceQueue queue = new ReferenceQueue();
        WeakReference ref = new WeakReference(new Animal("cat"), queue);
        System.out.println("ref isNull?:" + (ref.get() == null)
                + ",ref hashcode:" + ref.hashCode());

        Object obj = null;
        obj = queue.poll();
        System.out.println("ref2 isNull?:" + (ref.get() == null)
                + ",obj isNull?:" + (obj == null));

        System.gc();

        System.out.println("ref3 isNull?:" + (ref.get() == null)
                + ",obj isNull?:" + (obj == null));
        obj = queue.poll();
        System.out.println("ref4 isNull?:" + (ref.get() == null)
                + ",obj isNull?:" + (obj == null)
                + ",obj hashcode:" + obj.hashCode());
    }

    private static void testFunParam(Data data) {
        data.setName("dfdf");
        System.out.println("data in fun :" + data);
    }
}
