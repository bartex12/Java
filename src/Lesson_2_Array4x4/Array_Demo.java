package Lesson_2;

public class Array_Demo {
    public static void main(String[] args) {

        String[][] array44 = {{"10","11","12","13"},
                {"20","21","22","23"},{"30","31","32","33"},{"40","41","42","44"}};

        String[][] mistake = {{"10","11","12","13"},
                {"20","21","22","23"},{"30","31","32","33"},{"40","qqq","42","44"}};

        String[][] mistake2 = {{"10","11","12","13"}};

        try {
            test (array44); //получаем результат
            System.out.println("************************");
            test (mistake);  //обрабатываем ошибку - не число
        }catch (MyArrayDataExeption e){
            e.printStackTrace();
        }catch (MyArraySizeExeption e){
            e.printStackTrace();
        }

        try {
            System.out.println("************************");
            test(mistake2); //обрабатываем ошибку - неправильный размер массива
        }catch (MyArraySizeExeption e){
            e.printStackTrace();
        }catch (MyArrayDataExeption e){
            e.printStackTrace();
        }
    }

    // так как MyArrayDataExeption extends Exception, получаем uncheck исключение, которое требует throws
    //можно сделать extends RuntimeException, тогда throws не требуется
    public static void test (String[][] arr) throws MyArrayDataExeption {
        int stroka = arr.length;
        int stolbik = arr[0].length;

        if ((stroka!=4)||(stolbik!=4))throw new MyArraySizeExeption(
                "Требуется массив 4х4, введён массив " + stroka+"x"+stolbik);

        System.out.println("Строковый массив 4х4");
        int sum =0;
        int position= 0;
        for (int i = 0; i < stroka; i++) {
            for (int j = 0; j < stolbik; j++) {
                System.out.print(arr[i][j] + "\t");
                try {
                    position = Integer.parseInt(arr[i][j]);
                    sum += position;
                }catch (NumberFormatException е){
                    throw new MyArrayDataExeption("строка = " + (i+1) +" столбец = " + (j+1));
                }
            }
            System.out.println();
        }
        System.out.println("Сумма элементов массива = " + sum);
    }
}
