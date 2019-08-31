package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server  {

    private Vector<ClientHandler> clients; //синхро список клиентов

    public Server() throws SQLException {
        // готовим пул на 10 потоков
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // сразу запускаем поток для работы с базой данных, чтобы провести авторизацию
        executorService.execute(new DB_Runnable());

        clients =  new Vector<>();

        Socket socket = null;
        ServerSocket server = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                System.out.println("Подключение");
                executorService.execute( new ClientHandler(this, socket));
                Thread.currentThread().setName("ClientHandler");

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
            Auth_DB_Service.disconnect();  //отключаемся от базы данных
            executorService.shutdown();
        }
    }

    //отправка сообщения всем участникам чата
    public void broadcastMsg(ClientHandler from, String msg) {
        //перебираем клиентов (получателей!! сообщений)
        // и если конкретный получатель не в чёрном списке отправителя, посылаем ему сообщение
        for (ClientHandler o : clients) {
            if (!o.checkBlackList(from.getNick())) {
                o.sendMsg(msg);
            }
        }
    }

    // отправка личного сообщения
    public void broadcastPersonalMsg(String nickTo, String msg, ClientHandler clientHandler) {
        System.out.println("broadcastPersonalMsg");
        //попробую оператор break с меткой
        personalIs:
        {
            //перебираем клиентов и если ник текущего клиента равен требуемому,
            // а также если требуемый ник не входит в чёрный список ,
            // посылаем ему сообщение (И ТОЛЬКО ЕМУ)
            for (ClientHandler o : clients) {
                if (o.getNick().equals(nickTo)) {
                    if (!clientHandler.checkBlackList(nickTo)){
                        o.sendMsg("Личка от " + clientHandler.getNick() + ": " + msg);
                        clientHandler.sendMsg("В личку " + nickTo + " : " + msg);
                        break personalIs;
                    }else {
                        clientHandler.sendMsg(" Не отправлено: "+ nickTo + " в чёрном списке" );
                        break personalIs;
                    }
                }
            }
            clientHandler.sendMsg(" Нет участника с ником: " + nickTo);
        }
    }
//clientHandler.sendMsg(" Не отправлено: "+ nickTo + " в чёрном списке" );


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
                c.sendMsg(c.getNick() + " :" + " Добро пожаловать в чат!");
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
