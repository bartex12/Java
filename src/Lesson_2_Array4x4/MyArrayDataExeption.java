package Lesson_2;

public class MyArrayDataExeption extends Exception{
    private String s;

    public MyArrayDataExeption(String s){
        this.s = s;
        System.out.println("");
        System.out.println("Не число в позиции: " +s);
    }
}
