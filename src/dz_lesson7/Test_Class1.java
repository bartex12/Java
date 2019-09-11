package dz_lesson7;

public class Test_Class1 extends  Test_ClassBase {
    public Test_Class1() {
    }

    @Testing(level = 3)
    public void test1_class1() {
        System.out.print("test1_class1 level = ");
    }

    @Testing(level = 1)
    public void test2_class1(){
        System.out.print("test2_class1 level = ");
    }

    @Testing(level = 2)
    public void test3_class1(){
        System.out.print("test3_class1 level = ");
    }
}
