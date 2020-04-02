package com.acorn.testanything.string;

import androidx.annotation.NonNull;

/**
 * Created by acorn on 2020/3/29.
 */
public class StringTest {
    private static String upcase(String s) {
        return s.toUpperCase();
    }

    public static void main(String[] args) {
        String h = "hello";
        System.out.println(h);
        String hh = upcase(h);
        System.out.println(hh);
        System.out.println(h);

        String w = "world";
        System.out.println(w);
        String ww = w.toUpperCase();
        System.out.println(ww);
        System.out.println(w);

//        System.out.println(new StringTest().toString());
        System.out.println(String.format("我是浮点数%f,我是字符串%s.我是百分号%%",
                1.5f,"啊字符串"));
    }

    @NonNull
    @Override
    public String toString() {
        return "abc"+this; //这里调用this会导致无限递归,因为前面有字符串,所以自动调用了this.toString
    }
}
