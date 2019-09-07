package com.batiaev.java3.lesson6.dz_less6.subArray;

public class SubArr {

    public SubArr(){
    }

    public int[] getSubArray(int[] arr){

        int[] newArr =null;
        if (arr.length == 0){
            System.out.println("Ошибка ввода: длина входного массива = 0");
            return null;
        }else {
            toString("Входной массив: ", arr);
            boolean isFour = false;
            for (int i = arr.length-1; i>=0; i--){
                if(arr[i] == 4){
                    isFour = true;
                    int size = arr.length-i-1;
                    newArr = new int[size];
                    System.arraycopy(arr,i+1,newArr,0,size);
                    toString("Выходной массив: ", newArr);
                    break;
                }
            }
                if(!isFour){
                    throw new RuntimeException("ОШИБКА! Входной массив не содержит 4");
                }
            return newArr;
        }
    }

    private void toString(String beforeString, int[] arr){
        String s = beforeString;
            s+="[ ";
        if (arr.length>0) {
            for (int str : arr) {
                s += str + " ";
            }
        }
            s+="]";
        System.out.println(s);
    }
}
