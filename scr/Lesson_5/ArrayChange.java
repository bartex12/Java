package Lesson_5;

class ArrayChange_Demo{
    public static void main(String[] args) {

        final int number = 5000000;

        ArrayChange demo = new ArrayChange(number);
        demo.arrayChangeOneThread();

        System.out.println();
        System.out.println("**************************************");

        ArrayChange demo1 = new ArrayChange(number);
        demo1.arrayChangeTwoThreads();

    }
}


public class ArrayChange {
    int size;
    int half;
    float[] arr;

    public ArrayChange(int size) {
        this.size = size;
        half = size / 2;
        arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        System.out.println();
    }

    //***************************************************************
    // Метод расчёта в одном потоке
    void arrayChangeOneThread() {
        long a = System.currentTimeMillis();  // Время начала работы
        System.out.println(" Начало расчёта в одном потоке");
        System.out.println(size + " циклов" + " Ждите... ");
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Один поток. Общее время работы = = " +
                (System.currentTimeMillis() - a) + " миллисекунд");
    }
    //**************************************************************************


    public synchronized float[] arrayChange(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) *
                   Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return array;
    }


    // Метод расчёта в двух потоках
    void arrayChangeTwoThreads() {

        long start = System.currentTimeMillis(); //начало работы метода на 2 потока
        //Разбивка на 2 массива равной длины
        float[] arr1 = new float[half];
        float[] arr2 = new float[half];
        System.arraycopy(arr, 0, arr1, 0, half);
        System.arraycopy(arr, half, arr2, 0, half);

        System.out.println("Два потока. Время разбивки массива на 2 части = " +
                (System.currentTimeMillis() - start) + " миллисекунд");
        System.out.println();
        System.out.println("-----------------------------");


        //формирование потока 1
        float[] finalArr1 = arr1;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                long arrayBeforeChanging = System.currentTimeMillis(); //начало обработки в потоке

                arrayChange(finalArr1);

                System.out.println( "Два потока. Время изменения  массива в потоке 1 = " +
                        (System.currentTimeMillis() - arrayBeforeChanging) + " миллисекунд");
            }
        });

        //формирование потока 2
        float[] finalArr2 = arr2;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                long arrayBeforeChanging = System.currentTimeMillis(); //начало обработки в потоке

                arrayChange(finalArr2);

                System.out.println( "Два потока. Время изменения  массива в потоке 2 = " +
                        (System.currentTimeMillis() - arrayBeforeChanging) + " миллисекунд");
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------");

        long arrayBeforeJoint = System.currentTimeMillis(); //время начала объединения
        System.arraycopy(arr1, 0, arr, 0, half);
        System.arraycopy(arr2, 0, arr, half, half);

        System.out.println("Два потока. Время объединения  массива = " +
                (System.currentTimeMillis() - arrayBeforeJoint) + " миллисекунд");
        System.out.println("----------------------------");
        System.out.println("Два потока. Общее время работы = " +
                (System.currentTimeMillis() - start) + " миллисекунд");
    }

}





