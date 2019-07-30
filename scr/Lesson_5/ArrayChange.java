package Lesson_5;

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
            //System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }

    //***************************************************************
    // Метод расчёта в одном потоке
    void arrayChangeOneThread() {
        long a = System.currentTimeMillis();  // Время начала работы
        System.out.println(" Начало расчёта в 1 потоке");
        System.out.println(" Идёт расчёт. Ждите... ");
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            //System.out.println(arr[i]);
        }
        System.out.println("Один поток. Общее время работы = = " +
                (System.currentTimeMillis() - a) + " миллисекунд");
    }
    //**************************************************************************


    public synchronized float[] arrayChange(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) *
                   Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            //System.out.println(" array[i] = " + array[i]);
        }
        return array;
    }


    // Метод расчёта в двух потоках
    void arrayChangeTwoThreads() {

        long start = System.currentTimeMillis(); //начало работы метода на 2 потока
        //System.out.println("Расчёт в 2 потоках Текущий поток - " + Thread.currentThread());
       // System.out.println(" Идёт расчёт. Ждите... ");
        //Разбивка на 2 массива равной длины
        float[] arr1 = new float[half];
        float[] arr2 = new float[half];
        System.arraycopy(arr, 0, arr1, 0, half);
        System.arraycopy(arr, half, arr2, 0, half);

//        for (int i = 0; i<half; i++){
//            System.out.print("arr1 = " + arr1[i] + "   arr2 = " + arr2[i]+" \n");
//        }

        System.out.println("Два потока. Время разбивки массива на 2 части = " +
                (System.currentTimeMillis() - start) + " миллисекунд");
        System.out.println();
        System.out.println("-----------------------------");


        //формирование потока 1
        float[] finalArr1 = arr1;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Расчёт в 2 потоках Текущий поток - " + Thread.currentThread());
                long arrayBeforeChanging = System.currentTimeMillis(); //начало обработки в потоке

                arrayChange(finalArr1);

                //System.out.println("Расчёт в 2 потоках Текущий поток - " + Thread.currentThread());
                System.out.println( "Два потока. Время изменения  массива в потоке 1 = " +
                        (System.currentTimeMillis() - arrayBeforeChanging) + " миллисекунд");
            }
        });

        //формирование потока 2
        float[] finalArr2 = arr2;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Расчёт в 2 потоках Текущий поток - " + Thread.currentThread());
                long arrayBeforeChanging = System.currentTimeMillis(); //начало обработки в потоке

                arrayChange(finalArr2);

                //System.out.println("Расчёт в 2 потоках Текущий поток - " + Thread.currentThread());
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
        //System.out.println(" Размер массива arr = " + arr.length);
//        for (int i = 0; i < size; i++) {
//            System.out.println(arr[i]+ " ");
//        }
        System.out.println("Два потока. Время объединения  массива = " +
                (System.currentTimeMillis() - arrayBeforeJoint) + " миллисекунд");
        System.out.println("----------------------------");
        System.out.println("Два потока. Общее время работы = " +
                (System.currentTimeMillis() - start) + " миллисекунд");
    }

}

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




