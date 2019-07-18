package Lesson_2_Array4x4;

public class MyArraySizeExeption extends RuntimeException{

    private String s;

    public MyArraySizeExeption(String s){
        super(s);
        this.s = s;
    }
}
