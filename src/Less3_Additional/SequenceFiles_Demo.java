package Less3_Additional;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class SequenceFiles {
    private static void seqInputStreamWork() throws IOException {
        ArrayList<InputStream> arr = new ArrayList<>();
        arr.add(new FileInputStream("123/test1.txt"));
        arr.add(new FileInputStream("123/test2.txt"));
        arr.add(new FileInputStream("123/test3.txt"));
        arr.add(new FileInputStream("123/test4.txt"));
        arr.add(new FileInputStream("123/test5.txt"));
        SequenceInputStream seq = new SequenceInputStream(Collections.enumeration(arr));
        int x;
        while ((x=seq.read())!=-1){
            System.out.print((char)x);
        }
    }
}
