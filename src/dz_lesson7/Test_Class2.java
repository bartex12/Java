package dz_lesson7;

public class Test_Class2 extends  Test_ClassBase {
    @Testing(level = 9)
    public void test1_class2() {
        System.out.print("test1_class2 level = ");
    }

    @Testing(level = 7)
    public void test2_class2(){
        System.out.print("test2_class2 level = ");
    }

    @Testing(level = 5)
    public void test3_class2(){
        System.out.print("test3_class2 level = ");
    }
}
