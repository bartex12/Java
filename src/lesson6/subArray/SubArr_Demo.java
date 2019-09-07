package com.batiaev.java3.lesson6.dz_less6.subArray;

public class SubArr_Demo {

    public static void main(String[] args) {

        int[] arr1 = {1,2,3,4,5}; //Выходной массив: [ 5 ]
        int[] arr2 = {4,4,4,4}; //Выходной массив: [ ]
        int[] arr3 = {}; // Ошибка ввода: длина входного массива = 0
        int[] arr4 = {1,2,3}; //RuntimeException: ОШИБКА! Входной массив не содержит 4

        SubArr subArr= new SubArr();

        subArr.getSubArray(arr1);
        System.out.println();
        subArr.getSubArray(arr2);
        System.out.println();
        subArr.getSubArray(arr3);
        System.out.println();
        subArr.getSubArray(arr4);
    }
}
