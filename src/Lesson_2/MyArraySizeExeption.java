package Lesson_2;

public class MyArraySizeExeption extends RuntimeException{

    private String s;

    public MyArraySizeExeption(String s){
        super(s);
        this.s = s;
    }
}
