package com.acorn.testanything.genericity.food;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/Just_keep/article/details/79482365
 * Created by acorn on 2020/4/2.
 */
public class Test {
    public static void main(String[] args) {
        Fruit[] fruits = new Fruit[3];
        Food[] foods = fruits; //数组是协变的

        //泛型是不可变的
        List<Beef> beefList = new ArrayList<>();
        List<Food> foodList = new ArrayList<>();
        //err 不可协变
        //foodList = beefList;
        //err 不可逆变
        //beefList = foodList;
        addFood(foodList);
        //err 不可协变
        //addFood(beefList);

        //上界通配符
        List<? extends Food> foodList2 = new ArrayList<>();
        List<Apple> appleList2 = new ArrayList<>();
        //可以协变
        foodList2 = appleList2;
        //err 不能执行添加null以外的操作
        //foodList2.add(new Food());
        //foodList2.add(new Beef());
        //foodList2.add(new Apple());
        //foodList2.add(new Fruit());
        //ok 把子类引用赋值给父类显然是可以的
        Food food2 = foodList2.get(0);

        //下界通配符
        List<? super Fruit> fruitList3 = new ArrayList<>();
        List<Food> foodList3 = new ArrayList<>();
        //支持逆变
        fruitList3 = foodList3;
        //ok 只能添加Fruit或其子类
        fruitList3.add(new Apple());
        //err 只能添加Fruit或其子类
        //fruitList3.add(new Food());
        //err get出来的元素是Object类型
        //Fruit fruit3=fruitList3.get(0);
        Object obj = fruitList3.get(0);
    }

    public static void addFood(List<Food> foodList) {
        foodList.add(new Apple());
    }
}
