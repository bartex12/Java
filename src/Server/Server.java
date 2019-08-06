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

            AuthService.connect();  // подключаемся к базе данных
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Подключение");
               // clients.add( new ClientHandler(this, socket)); //так было
                new ClientHandler(this, socket);
                //subscribe(new ClientHandler(this, socket)); //так правильно (потом удалить subscribe)
                //this.broadcastMsgBegin(clients.lastElement());// сообщаем всем о появлении нового участника чата (потом удалить)
                //System.out.println("Новый клиент = " + clients.lastElement()+ " socket = " + socket);
                //System.out.println("Всего клиентов = " + clients.size());
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
            AuthService.disconnect();  //отключаемся от базы данных
        }
    }

    public void broadcastMsg(String msg){
        for (ClientHandler c: clients){
            c.sendMsg(msg);
        }
    }

    public void broadcastPersonalMsg(String personalNick, String personalMessage){
        for (ClientHandler c: clients){
        }
    }


    public void subscribe(ClientHandler client) {
        clients.add(client);
        for (ClientHandler c: clients){
            if (!c.equals(client)){
                c.sendMsgBegin();
            }
        }
        System.out.println("Всего клиентов = " + clients.size());
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        System.out.println("Осталось клиентов = " + clients.size());
        for(ClientHandler c: clients){
            c.sendMsgStopped();
        }
    }

    //    //перенёс функционал в метод subscribe
//    public void broadcastMsgBegin(ClientHandler client){
//        for (ClientHandler c: clients){
//            if (!c.equals(client)){
//                c.sendMsgBegin();
//            }
//        }
//    }

//    //метод заменён на более правильный метод  public void unsubscribe(ClientHandler client),
//    // дополненный функционалом этого метода
//    public void broadcastMsgClosed(Socket socket){
//        ClientHandler temp = null;
//        for (ClientHandler c: clients){
//            if (c.getSocket().equals(socket)){
//                c.sendMsgClosed();
//                temp = c;  //иначе исключение про изменение коллекции во время перебора
//            }
//        }
//        clients.remove(temp);
//        System.out.println("Осталось клиентов = " + clients.size());
//        //остальным передаём, что этот покинул чат
//        for(ClientHandler c: clients){
//            c.sendMsgStopped();
//        }
//    }

}
