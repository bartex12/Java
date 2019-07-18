package Lesson_2;

public class MyArraySizeExeption extends Exception{
    private String s;

    public MyArraySizeExeption(String s){
        this.s = s;
        System.out.println("");
        System.out.println("Не число в позиции: " +s);
    }
}
