package Less4_ABC;

// Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок
//– ABСABСABС). Используйте wait/notify/notifyAll.
// Тупо переделанный пример из методички :)

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
                        //ждём на мониторе объекта mon пока не выполнится условие
                        while (currentLetter != 'A') {
                            //метод wait() вызывается только из синхронизированного блока
                            //ждём оповещения для того чтобы продолжить
                            mon.wait();
                        }
                        //условие выполнено, захватываем монитор, печатаем А, обязываем дальше печатать В,
                        // посылаем уведомление всем потокам, освобождаем монитор
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
