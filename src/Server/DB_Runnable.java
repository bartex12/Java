package Server;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB_Runnable implements Runnable {
    private static Logger log = Logger.getLogger(DB_Runnable.class.getName());
    @Override
    public void run() {
        try {
            Thread.currentThread().setName("DB_Service");
            Auth_DB_Service.connect();  // подключаемся к базе данных
            System.out.println("Асинхронная задача " + Thread.currentThread().getName() + " запущена");
            log.info("Asynchronous task " + Thread.currentThread().getName() + " started");
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "SQLException", e);
        }
    }
}
