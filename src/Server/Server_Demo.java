package Server;

import java.sql.SQLException;

public class Server_Demo {
    public static void main(String[] args) {
        try {
            new Server();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
