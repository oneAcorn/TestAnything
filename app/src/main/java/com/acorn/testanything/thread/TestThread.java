package com.acorn.testanything.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by acorn on 2020/4/13.
 */
public class TestThread {
    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        final WhateverClass whateverClass = new WhateverClass();
        whateverClass.name = "zhang";
        whateverClass.abc = "san";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (whateverClass) {
                        Thread.sleep(450);
                        whateverClass.setName("wang");
                        Thread.sleep(2000);
                        whateverClass.setAbc("wu");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (whateverClass) {
                    try {
                        System.out.println("1 " + whateverClass.toString());
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(whateverClass.toString());
                }
            }
        }).start();
    }

    public static void test2() {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 30; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    output("a");
                    output("b");
                    output("c");
                    synchronized (this) {
                        output("d");
                        output("e");
                        output("f");
                    }
                }
            });
        }
    }

    public static void output(String msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }

    static class WhateverClass {
        private String name;
        private String abc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbc() {
            return abc;
        }

        public void setAbc(String abc) {
            this.abc = abc;
        }


        @NonNull
        @Override
        public String toString() {
            return name + "_" + abc;
        }
    }
}
