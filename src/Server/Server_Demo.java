package Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server_Demo {
    public static void main(String[] args) {

        //использует java.util.logging framework конфигурация в файле logging.properties
        try {
            LogManager.getLogManager().readConfiguration(
                    Server_Demo.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Failed to load logger configuration: " + e.toString());
        }catch (NullPointerException e){
            System.err.println("Logger configuration file not found: " + e.toString());
        }

        Logger log = Logger.getLogger(Auth_DB_Service.class.getName());

        try {
            new Server();
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "SQLException", e);
        }
    }
}
