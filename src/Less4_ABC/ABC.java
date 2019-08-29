package Lesson4;

//    Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок
//– ABСABСABС). Используйте wait/notify/notifyAll.
public class ABC {

        private final Object mon = new Object();
        private volatile char currentLetter = 'A';
        final int COUNT = 5;

        public static void main(String[] args) {
            ABC w = new ABC();
            Thread t1 = new Thread(() -> {
                w.printA();
            });
            Thread t2 = new Thread(() -> {
                w.printB();
            });
            Thread t3 = new Thread(() -> {
                w.printC();
            });
            t1.start();
            t2.start();
            t3.start();
        }

        public void printA() {
            synchronized (mon) {
                try {
                    for (int i = 0; i < COUNT; i++) {
                        while (currentLetter != 'A') {
                            mon.wait();
                        }
                        System.out.print("A");
                        currentLetter = 'B';
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) { e.printStackTrace();
                }
            }
        }

        public void printB() {
            synchronized (mon) {
                try {
                    for (int i = 0; i < COUNT; i++) {
                        while (currentLetter != 'B') {
                            mon.wait();
                        }
                        System.out.print("B");
                        currentLetter = 'C';
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) { e.printStackTrace();
                }
            }
        }

        public void printC() {
            synchronized (mon) {
                try {
                    for (int i = 0; i < COUNT; i++) {
                        while (currentLetter != 'C') {
                            mon.wait();
                        }
                        System.out.print("C");
                        currentLetter = 'A';
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) { e.printStackTrace();
                }
            }
        }
}
