package Lesson_2_Array4x4;

public class MyArrayDataExeption extends Exception{
    private String s;

    public MyArrayDataExeption(String s){
        this.s = s;
        System.out.println("");
        System.out.println("Не число в позиции: " +s);
    }
}
