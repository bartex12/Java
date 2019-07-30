package Lesson_5;

public class ArrayChange_Demo {
    int size ;
    int half;
    float[] arr;
    Object object;

    public ArrayChange_Demo(int size){
        this.size = size;
        half = size/2;
        arr = new float[size];
        for(int i = 0; i<size; i++){
            arr[i] = 1;
        }

        object = new Object();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float[] arrayChange(float[] array){
        for (int i = 0; i< array.length; i++){
//            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) *
//                    Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            array[i] =  array[i]*i;
                    System.out.println(" array[i] = " + array[i]);
        }
        return array;
    }

    void arrayChangeOneThread(){
        long a = System.currentTimeMillis();  // Время начала работы
        System.out.println(" Начальное время = " + a + " миллисекунд");
        for (int i = 0; i< size; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            //System.out.println(" arr[i] = " + arr[i]);
        }
        System.out.println( "Один поток. Время выполнения цикла расчёта = " +
                (System.currentTimeMillis() - a) + " миллисекунд");
    }

//    void arraySplit(float[] array, int newSize){
//        long start = System.currentTimeMillis(); //начало работы метода на 2 потока
//        System.out.println(" Начальное время = " + start + " миллисекунд");
//        //Разбивка на 2 массива равной длины
//        float[] arr1 = new float[newSize];
//        float[] arr2 = new float[newSize];
//        System.arraycopy(array,0, arr1,0, newSize-1);
//        System.arraycopy(array, newSize, arr1,0, newSize);
//        System.out.println( "Два потока. Время разбивки массива на 2 части = " +
//                ( System.currentTimeMillis() - start) + " миллисекунд");
//    }

    void arrayChangeTwoThreads(){
        long start = System.currentTimeMillis(); //начало работы метода на 2 потока
        System.out.println(" Начальное время = " + start + " миллисекунд");
        //Разбивка на 2 массива равной длины
        float[] arr1 = new float[half];
        float[] arr2 = new float[half];
        System.arraycopy(arr,0, arr1,0, half-1);
        System.arraycopy(arr, half, arr1,0, half);
        System.out.println( "Два потока. Время разбивки массива на 2 части = " +
                ( System.currentTimeMillis() - start) + " миллисекунд");

//        //формирование потока 1
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long arrayBeforeChanging = System.currentTimeMillis(); //начало обработки в потоке
//                //System.out.println( "Внутри потока 1");
//                ArrayChange_Demo.arrayChange(arr1);
//                System.out.println( "Два потока - поток 1: Время изменения  массива = " +
//                        (System.currentTimeMillis() - arrayBeforeChanging) + " миллисекунд");
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long arrayBeforeChanging = System.currentTimeMillis(); //начало обработки в потоке
//                //System.out.println( "Внутри потока 2");
//                ArrayChange_Demo.arrayChange(arr2);
//                System.out.println( "Два потока - поток 2: Время изменения  массива = " +
//                        (System.currentTimeMillis() - arrayBeforeChanging) + " миллисекунд");
//            }
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        synchronized (object){
           arrayChange();
        }

        long arrayBeforeJoint = System.currentTimeMillis(); //время начала объединения
        System.arraycopy(arr1,0, arr,0, half);
        System.arraycopy(arr2,0, arr, half, half);
        //System.out.println(" Размер массива arr = " + arr.length);
        System.out.println( "Два потока. Время объединения  массива = " +
                (System.currentTimeMillis() - arrayBeforeJoint) + " миллисекунд");

        System.out.println( "Два потока. Общее время работы = " +
                (System.currentTimeMillis() - start) + " миллисекунд");
    }


    public static void main(String[] args) {
//        ArrayChange_Demo demo = new ArrayChange_Demo(10000000);
        ArrayChange_Demo demo = new ArrayChange_Demo(10);
        //demo.arrayChangeOneThread();
        demo.arrayChangeTwoThreads();
    }


}
