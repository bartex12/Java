package TreeSetString;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetString_Test {

    public static void main(String[] args) {
//        String str1 = "Строка1";
//        String str2 = "Строка2";
//        String str3 = "Строка0";

        Integer i1 = 1;
        Set<Integer> test = new TreeSet<Integer>();
//        Set<String> test = new TreeSet<String>();
        System.out.println(" Создан экземпляр:" + test.getClass().toString());
        test.add(i1);
        for (Integer i: test){
            System.out.println(i);
        }
    }
}
