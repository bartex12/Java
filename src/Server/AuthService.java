package Server;

import java.sql.*;

public class AuthService {

    public static Connection connection;
    public static Statement statement;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:sqlite:mainBaseSQLite.db");
        statement = connection.createStatement();
    }

    public static String getNickByLoginAndPass(String login, String pass) throws SQLException {
        String query = String.format("select nickname from main where login = '%s' and password = '%s'",login, pass);
        ResultSet rs = statement.executeQuery(query);

        if (rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
