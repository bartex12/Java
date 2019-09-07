package com.batiaev.java3.lesson6.dz_less6.array1And4;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


public class Array1And4_Test {

    int[] arr1  = {1,1,4,1,1};
    int[] arr2  = {1,4,4,4,4,4};
    int[] arr3  = {1,1,1,1,1};
    int[] arr4  = {4,4,4,4,4,4};

    private Array1And4 array1And4;

    @BeforeClass
    public static void startTests(){
        System.out.println("Тестируем метод  is_1_Or_4() класса Array1And4");
    }

    @Before
    public void staetTests(){
        System.out.println("--------------------------------------------");
        array1And4 =new Array1And4();
    }

    @AfterClass
    public static void endTest(){
        System.out.println("-----------------------");
        System.out.println("Тестирование закончено");
    }

    @Test
    public  void test_Many1_And4(){
        Assert.assertTrue(array1And4.is_1_Or_4(arr1) );
    }

    @Test
    public  void test_Many4_And1(){
        Assert.assertTrue(array1And4.is_1_Or_4(arr2) );
    }

    @Test
    public  void test_Only1(){
        Assert.assertFalse(array1And4.is_1_Or_4(arr3) );
    }

    @Test
    public  void test_Only4(){
        Assert.assertFalse(array1And4.is_1_Or_4(arr4) );
    }

}