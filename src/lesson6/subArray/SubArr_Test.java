package com.batiaev.java3.lesson6.dz_less6.subArray;

import org.junit.*;

public class SubArr_Test {

    int[] arr1 = {5};
    int[] arr11 = {1,2,3,4,5};

    int[] arr2 = {};
    int[] arr22 = {4,4,4,4};

    //int[] arr3 = null;
    int[] arr33 = {};

    int[] arr4 = {};
    int[] arr44 = {1,2,3};

    @BeforeClass
    public static void startTests(){
        System.out.println("Тестируем метод  getSubArray() класса SubArr");
    }

    SubArr sa;

    @Before
    public void startTest(){
        System.out.println("--------------------------------------------");
        sa = new SubArr();
    }

    @AfterClass
    public static void endTest(){
        System.out.println("-----------------------");
        System.out.println("Тестирование закончено");
    }

    @Test
    public void test_Is1Element(){
        Assert.assertArrayEquals(arr1, sa.getSubArray(arr11));
    }

    @Test
    public void test_Is0Element(){
        Assert.assertArrayEquals(arr2, sa.getSubArray(arr22));
    }

    @Test
    public void test_HoleArray(){
        Assert.assertArrayEquals(null, sa.getSubArray(arr33));
    }

    @Test(expected = RuntimeException.class)
    public void test_No4InArray(){
        Assert.assertArrayEquals(arr4, sa.getSubArray(arr44));
    }

}
