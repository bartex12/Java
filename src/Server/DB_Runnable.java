package Server;

import java.sql.SQLException;

public class DB_Runnable implements Runnable {
    @Override
    public void run() {
        try {
            Thread.currentThread().setName("DB_Service");
            Auth_DB_Service.connect();  // подключаемся к базе данных
            System.out.println("Асинхронная задача " + Thread.currentThread().getName() + " запущена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
