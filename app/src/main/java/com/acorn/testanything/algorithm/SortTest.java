package com.acorn.testanything.algorithm;

/**
 * Created by acorn on 2019/10/9.
 */
public class SortTest {

    public static void main(String[] args) {
        int[] arr = new int[]{12, 6, 22, 1, 3, 47, 63, 2};
        SortTest test = new SortTest();
        test.printArr(test.bubleSort(arr));
    }

    /**
     * 冒泡排序
     * @param arr
     * @return
     */
    private int[] bubleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        return arr;
    }

    private void printArr(int[] arr) {
        if (null == arr || arr.length == 0)
            return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i != 0)
                sb.append(",");
            sb.append(arr[i]);
        }
        System.out.println(sb.toString());
    }
}
