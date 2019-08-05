package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server  {

    private Vector<ClientHandler> clients;


    public Server() throws SQLException {

        clients =  new Vector<>();
        Socket socket = null;
        ServerSocket server = null;

        try {
//            //тест
//            AuthService.connect();  // подключаемся к базе данных
//            String s = AuthService.getNickByLoginAndPass("login1","pass1");
//            System.out.println(s);

            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                clients.add( new ClientHandler(this, socket)); //так было
               // subscribe(new ClientHandler(this, socket)); //так правильно
                this.broadcastMsgBegin(clients.lastElement());// сообщаем всем о появлении нового участника чата
                System.out.println("Новый клиент = " + clients.lastElement()+ " socket = " + socket);
                System.out.println("Всего клиентов = " + clients.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // AuthService.disconnect();  //отключаемся от базы данных
        }
    }

    public void broadcastMsgBegin(ClientHandler client){
        for (ClientHandler c: clients){
            if (!c.equals(client)){
                c.sendMsgBegin();
            }
        }
    }

    public void broadcastMsg(String msg){
        for (ClientHandler c: clients){
            c.sendMsg(msg);
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void broadcastMsgClosed(Socket socket){
        ClientHandler temp = null;
        for (ClientHandler c: clients){
            if (c.getSocket().equals(socket)){
                c.sendMsgClosed();
                temp = c;  //иначе исключение про изменение коллекции во время перебора
            }
        }
        clients.remove(temp);
        System.out.println("Осталось клиентов = " + clients.size());
        //остальным передаём, что этот покинул чат
        for(ClientHandler c: clients){
            c.sendMsgStopped();
        }
    }

}
