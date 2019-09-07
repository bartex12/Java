package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server  {

    private static Logger log = Logger.getLogger(Server.class.getName());
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
            log.info("Server is running");

            while (true){
                socket = server.accept();
                System.out.println("Подключение");
                log.info("Connection");
                executorService.execute( new ClientHandler(this, socket));
                Thread.currentThread().setName("ClientHandler");

            }
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "IOException: ", e);
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.log(Level.SEVERE, "IOException: ", e);
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.log(Level.SEVERE, "IOException: ", e);
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
        System.out.println("Передача личного сообщения");
        log.info("Send a private message");
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
                        log.info("A private message from "+clientHandler.getNick()+": " + msg );
                        clientHandler.sendMsg("В личку " + nickTo + " : " + msg);
                        log.info("A private message to "+nickTo + ": " + msg );
                        break personalIs;
                    }else {
                        clientHandler.sendMsg(" Не отправлено: "+ nickTo + " в чёрном списке" );
                        log.warning("Not send! " + nickTo+ " in black list");
                        break personalIs;
                    }
                }
            }
            clientHandler.sendMsg(" Нет участника с ником: " + nickTo);
            log.warning("No member with a nickname " + nickTo);
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
        log.info("Number of clients = " + clients.size());
    }

    //отписка нового клиента (удаление из списка)
    public void unsubscribe(ClientHandler client, String nick) {
        clients.remove(client);
        System.out.println("Осталось клиентов = " + clients.size());
        log.info("Clients left = " + clients.size());
        for(ClientHandler c: clients){
            c.sendMsgStopped(nick);
        }
    }
}
