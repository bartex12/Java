package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Auth_DB_Service {

    private static Logger log = Logger.getLogger(Auth_DB_Service.class.getName());

    public static Connection connection;
    public static Statement statement;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "ClassNotFoundException: ", e);
        }
        connection = DriverManager.getConnection("jdbc:sqlite:mainBaseSQLite.db");
        statement = connection.createStatement();
    }

    //получаем hash code для 5 паролей в базе данных
    public static int[] getAllHash() throws SQLException{
                int[] hash = {"pass1".hashCode(),"pass2".hashCode(),
                        "pass3".hashCode(),"pass4".hashCode(),"pass5".hashCode()};
                for (int i = 0; i < hash.length; i++ ){
                    System.out.println(hash[i]);
//                    106438208
//                    106438209
//                    106438210
//                    106438211
//                    106438212
                }
               return  hash;
    }

    //изменяем пароль для логина
    public static void insertTableWithHash(String pass, String login) throws SQLException {
        String query = String.format("UPDATE  main set password = %s where login = '%s'",
                pass, login);
        statement.executeUpdate(query);
    }

    //получаем ник по логину и паролю
    public static String getNickByLoginAndPass(String login, int hashPass) throws SQLException {
        String query = String.format("select nickname from main where login = '%s' and password = '%d'",login, hashPass);
        ResultSet rs = statement.executeQuery(query);

        if (rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    //добавляем ник в чёрный список - у каждого пользователя свой чёрный список
    public static void addNickToBlacklist(String user_nick, String user_block) throws SQLException {
        String query = String.format("INSERT into blacklist(user_nick, user_block) VALUES ('%s', '%s')", user_nick, user_block);
        statement.executeUpdate(query);
    }

    //получаем чёрный список по нику пользователя
    public static List<String> getNicksFromBlacklist(String user_nick) throws SQLException {
        String query = String.format("SELECT distinct user_block from blacklist where user_nick = '%s';", user_nick);
        try {
            ResultSet rs = statement.executeQuery(query);
            List<String> blacklist = new ArrayList<>();
            while (rs.next()){
                blacklist.add( rs.getString(1));
            }
            return blacklist;
        }catch (NullPointerException e){
            e.printStackTrace();
            log.log(Level.SEVERE, "NullPointerException: ", e);
        }
        return null;
    }


    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "SQLException: ", e);
        }
    }

}
