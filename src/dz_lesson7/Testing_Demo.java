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

        ArrayList<Method> arr = new ArrayList<>();

        //получаем методы класса
        Method[] methods = testClass.getMethods();
        //перебираем методы
        for (Method m: methods){
            //если аннотация метода ==beforeSuite
            if (m.getAnnotation(beforeSuite.class)!=null){
                try {
                    //запускаем данный метод
                    m.invoke(testClass.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException |
                        NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            //если аннотация Testing, добавляем в список методов
            if (m.getAnnotation(Testing.class)!=null){
                arr.add(m);
            }
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

        // //если аннотация метода ==afterSuite
        methods = testClass.getMethods();
        for (Method m: methods){
            //если аннотация метода ==afterSuite
            if (m.getAnnotation(afterSuite.class)!=null){
                //System.out.println("!=null ");
                try {
                    //запускаем данный метод
                    m.invoke(testClass.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException |
                        NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
