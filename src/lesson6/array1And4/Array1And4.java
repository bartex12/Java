package com.batiaev.java3.lesson6.dz_less6.array1And4;

import java.util.Arrays;

public class Array1And4 {

    public Array1And4(){
    }
    //предполагается, что массив состоит только из 1 и/или 4
    // - другие цифры или символы не проверяются (по условиям задачи)
    public boolean is_1_Or_4(int[] arr){
        float sum = Arrays.stream(arr).sum();
        float size = arr.length;
        float result = sum/size;
        System.out.println("sum = " + sum + " size = " + size +
                " sum/size = " + sum/size);
        return (result ==1f || result == 4f) ? false: true;
    }

}