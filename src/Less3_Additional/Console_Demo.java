package Less3_Additional;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Console_Demo {
    public static void main(String[] args) {
        try {
            consoleBook();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //консольное приложение Книга
    private static void consoleBook() throws IOException {

        System.out.println("Консольное приложение \"Книга\" ");
        final int PAGE_SIZE = 2000;// размер страницы  2000 символов/100 символов в строке  = 20 строк
        final int LINES = 100000; //количество строк по 100 случайных символов
        byte[] bt = new byte[PAGE_SIZE]; //размер буфера = размеру страницы

        File file = new File("123/book.txt");
        //file.mkdirs(); //создаём папку 123
        file.createNewFile(); //создаем текстовый файл

        ArrayList<String> arr = new ArrayList<>(LINES); //ArrayList подходящего размера
        Random rnd = new Random();
        for (int i = 0; i<LINES; i++ ){
            String str = "";
            for (int jj = 0; jj<100; jj++){
                int к = 65 + rnd.nextInt(60);
                str += (char)к; //собираем строку из 100 символов
            }
            arr.add(str); //добавляем строку в ArrayList
        }
        //заполняем файл строками из ArrayList
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for (String ii: arr){
                bw.write(ii);
                bw.write(System.getProperty("line.separator"));
            }
            bw.flush();
            arr.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long size = file.length(); //узнаём размер файла
        System.out.println("Размер файла = " + size);
        long pageCountMax;

        //рассчитываем количество страниц
        if (size%PAGE_SIZE>0){
            pageCountMax = size/PAGE_SIZE+1; //рассчитываем количество страниц
        }else {
            pageCountMax = size/PAGE_SIZE; //рассчитываем количество страниц
        }
        System.out.println("Страниц в файле = " + pageCountMax);

        //Метод, реализующий консольное приложение
        //читаем файл постранично
        RandomAccessFile raf= new RandomAccessFile(file,"r");
        System.out.println("*** Для выхода введите //\n");
        System.out.println("Введите номер страницы число от 1 до " + pageCountMax);
        Scanner in = new Scanner(System.in);

        while (true){
            String s = in.nextLine();
            if (s.trim().startsWith("//")){
                raf.close();
                System.exit(0);
            }else if (s.trim().equals("")) {
                System.out.println("Введите число");
            }else {
                long pageNumber = Long.parseLong(s);
                if (pageNumber<1)pageNumber = 1;
                if (pageNumber > pageCountMax) pageNumber = pageCountMax;

                System.out.println("Страница: " + pageNumber + " из " + pageCountMax);

                raf.seek((pageNumber-1)*(PAGE_SIZE));
                int x = 0;
                x = raf.read(bt,0,PAGE_SIZE);
                System.out.println(new String(bt, 0,x));

            }
        }

    }
}
