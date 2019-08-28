package Less3_Additional;

import java.io.*;

public class ReadBytes_Demo {

    public static void main(String[] args) throws IOException {
        System.out.println("********* ввод из файла через массив байт **********");
        System.out.println();
        byte[] arr = new byte[1024];
        FileInputStream in2 = new FileInputStream("123/test.txt");
        int x;
        while ((x = in2.read(arr))>0){
            System.out.print(new String(arr,0, x));
        }
        System.out.println();
        System.out.println();

        System.out.print("Размер файла = " + (new File("123/test.txt")).length());
    }
}
