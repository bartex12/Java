package dz_lesson7;

import dz_lesson7.Test_ClassBase.Testing;
import dz_lesson7.Test_ClassBase.afterSuite;
import dz_lesson7.Test_ClassBase.beforeSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class Testing_Demo {
    public static void main(String[] args) {
        start(Test_Class1.class);
        System.out.println("------------------------------");
        start(Test_Class2.class);
    }

    public static void  start(Class testClass){

        // 1- перебираем методы класса, запоминаем методы с  аннотацией beforeSuite и afterSuite,
        // формируем список методов с аннотацией Testing
        // 2-Запускаем метод с аннотацией beforeSuite
        // 3-сортируем тестовые методы по параметрам аннотаций
        // 4-запускаем тестовые методы по порядку сортировки
        // 5-Запускаем метод с аннотацией afterSuite

        ArrayList<Method> arr = new ArrayList<>();
        int beforNumber = -1;  //считаем методы с аннотацией beforeSuite
        int afterNumber = -1;  //считаем методы с аннотацией afterSuite

        //получаем методы класса, включая методы суперкласса
        Method[] methods = testClass.getMethods();
        Method befor = null;
        Method after = null;

        //перебираем методы
        for (Method m: methods){
            //если аннотация метода ==beforeSuite, считаем их количество
            if (m.getAnnotation(beforeSuite.class)!=null){
                befor =m;
                    beforNumber++;
            }
            //если аннотация Testing, добавляем в список методов
            if (m.getAnnotation(Testing.class)!=null){
                arr.add(m);
            }
            //если аннотация afterSuite, считаем их количество
            if (m.getAnnotation(afterSuite.class)!=null){
                after = m;
                afterNumber++;
            }
        }
        if (beforNumber>0){
            throw new RuntimeException(" Более одного метода с аннотацией @beforeSuite");
        }
        if (afterNumber>0){
            throw new RuntimeException(" Более одного метода с аннотацией @afterSuite");
        }

        // Запускаем метод с аннотацией beforeSuite
        try {
            befor.invoke(testClass.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //сортируем тестовые методы по параметрам аннотаций
        arr.sort(new Comparator<Method>() {
            @Override
            public int compare(Method one, Method two) {
                Testing testOne = one.getAnnotation(Testing.class);
                Testing testTwo = two.getAnnotation(Testing.class);
                return (testOne.level() < testTwo.level()) ? -1 :
                        ((testOne.level() == testTwo.level()) ? 0 : 1);
            }
        });
        //запускаем тестовые методы по порядку
        for (Method m: arr){
            try {
                m.invoke(testClass.getConstructor().newInstance());
                System.out.println(m.getAnnotation(Testing.class).level());
            }catch (InstantiationException | IllegalAccessException |
                    NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        // Запускаем метод с аннотацией afterSuite
        try {
            after.invoke(testClass.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
