package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server  {

    private Vector<ClientHandler> clients; //синхро список клиентов

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
                new ClientHandler(this, socket);
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

    //отправка сообщения всем участникам чата
    public void broadcastMsg(String msg){
        for (ClientHandler c: clients){
            c.sendMsg(msg);
        }
    }

    // отправка личного сообщения
    public void broadcastPersonalMsg(String nickTo, String msg, ClientHandler clientHandler) {
        System.out.println("broadcastPersonalMsg");
        //попробую оператор break с меткой
        personalIs:
        {
            for (ClientHandler client : clients) {
                if (client.getNick().equals(nickTo)) {
                    client.sendMsg("Личка от " + clientHandler.getNick() + ": " + msg);
                    clientHandler.sendMsg("В личку " + nickTo + " : " + msg);
                    break personalIs;
                }
            }
            clientHandler.sendMsg(" Нет участника с ником: " + nickTo);
        }
    }

    // есть ли участник с ником nick в чате
    public boolean isTheSame(String nick){
        for (ClientHandler client: clients){
            if (client.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }

    //подписка нового клиента (добавление в список)
    public void subscribe(ClientHandler client, String nick) {
        clients.add(client);
        for (ClientHandler c: clients){
            if (!c.equals(client)){
                c.sendMsgBegin(nick);
            }else {
                c.sendMsg(c.getNick() + " Добро пожаловать в чат!");
            }
        }
        System.out.println("Всего клиентов = " + clients.size());
    }

    //отписка нового клиента (удаление из списка)
    public void unsubscribe(ClientHandler client, String nick) {
        clients.remove(client);
        System.out.println("Осталось клиентов = " + clients.size());
        for(ClientHandler c: clients){
            c.sendMsgStopped(nick);
        }
    }
}
