package com.batiaev.java3.lesson6.dz_less6.array1And4;

public class Array1And4_Demo {

    //входной массив должен состоять только из 1 и 4
    public static void main(String[] args) {
        int[] arr1 = {1,1,1,1,1};
        int[] arr2 = {4,4,4,4,4,4};
        int[] arr3 = {1,1,4,1,1};
        int[] arr4 = {1,4,4,4,4,4};

        Array1And4 array1And4 = new Array1And4();
        array1And4.is_1_Or_4(arr1);
        array1And4.is_1_Or_4(arr2);
        array1And4.is_1_Or_4(arr3);
        array1And4.is_1_Or_4(arr4);
    }
}
