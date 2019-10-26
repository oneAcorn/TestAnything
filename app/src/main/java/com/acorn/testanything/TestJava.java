package com.acorn.testanything;

import android.os.Handler;

import java.util.regex.Pattern;

/**
 * Created by acorn on 2019-06-06.
 */
public class TestJava {
    public static void main(String[] args) {
        System.out.println(testRex("sadjfldasjf"));
        System.out.println(testRex("sadjf321ldjf"));
        System.out.println(testRex("sajfl2@asjf"));
        System.out.println(testRex("321312312"));
        System.out.println(testRex("@#$!$$!#!@#@!"));
    }

    public static boolean testRex(String input){
        return Pattern.compile("^(?=.[a-zA-Z])(?=.\\d)\\[^\\]{8,16}$").matcher(input).find();
    }
}
