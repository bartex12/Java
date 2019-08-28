package Less3_Additional;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class SequenceFiles_Demo  {

    public static void main(String[] args) throws IOException{
        ArrayList<InputStream> arr = new ArrayList<>();

        arr.add(new FileInputStream("123/1.txt"));
        arr.add(new FileInputStream("123/2.txt"));
        arr.add(new FileInputStream("123/3.txt"));
        arr.add(new FileInputStream("123/4.txt"));
        arr.add(new FileInputStream("123/5.txt"));

        SequenceInputStream seq = new SequenceInputStream(Collections.enumeration(arr));
        int x;
        while ((x=seq.read())!=-1){
            System.out.print((char)x);
        }
    }
}
