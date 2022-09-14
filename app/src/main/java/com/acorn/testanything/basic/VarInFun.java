package com.acorn.testanything.basic;

import com.acorn.testanything.genericity.Animal;
import com.acorn.testanything.genericity.Cat;

/**
 * Created by acorn on 2022/9/14.
 */
class VarInFun {
    public static void main(String[] args) {
        VarInFun test = new VarInFun();
        Integer a = 5;
        System.out.println("a赋值前的地址:" + System.identityHashCode(a));
        test.add3(a);
        System.out.println("a=" + a + ".a赋值前的地址:" + System.identityHashCode(a));
        a = 10;
        System.out.println("a=" + a + ".a修改值后的地址:" + System.identityHashCode(a));
        System.out.println(a);

        int d = 1;
        Integer e = 2;
        int[] arr = new int[]{3, 2, 3};
        test.test(d, e, arr);
        System.out.println(d + "," + e + "," + arr[0]);

        String aa = "3333";
        test.test2(aa);
        System.out.println(aa);

        Animal animal = new Animal("name");
        test.test3(animal);
        System.out.println("animal:" + animal.getName());
    }

    private void test(int a, Integer b, int[] c) {
        a = 4444;
        b = 44444;
        c[0] = 444444;
    }

    private void test2(String a) {
        a = "123";
    }

    private void test3(Animal animal) {
        animal.setName("123");
    }

    public void add3(Integer i) {
        System.out.println("i赋值前的地址:" + System.identityHashCode(i));
        i = 3;
        System.out.println("i赋值后的地址:" + System.identityHashCode(i));

    }
}
