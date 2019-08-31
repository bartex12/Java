package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Server server;
    List<String> blackList;
    private String nick;

    public String getNick() {
        return nick;
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    public ClientHandler(Server server, Socket socket){

        try {
            this.blackList = new ArrayList<>();
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                        try {

                            //блок авторизации
                            while (true){
                                System.out.println("ClientHandler цикл авторизации ");
                                String str = in.readUTF();  //принимаем сообщение
                                if (str.startsWith("/auth")){
                                    System.out.println("ClientHandler цикл авторизации str = " + str);
                                    String[] tokens = str.split(" ");
                                    int hash = tokens[2].hashCode();
                                    String newNick = Auth_DB_Service.getNickByLoginAndPass(tokens[1],hash);
                                    if (newNick!=null){
                                        if (!server.isTheSame(newNick)){
                                            sendMsg("/authok " + newNick);
                                            nick = newNick;
                                            System.out.println("Получен ник = " + nick);
                                            //добавляем участника в список участников и сообщаем о новом участнике чата
                                            server.subscribe(ClientHandler.this, nick);
                                            break;
                                        }else {
                                            sendMsg(" Такой логин/пароль уже используется");
                                        }
                                    }else {
                                        sendMsg(" Неверный логин/пароль ");
                                    }
                                }
                            }

                            // блок обработки сообщений в чате
                            while (true){
                                    String str = in.readUTF();  //принимаем сообщение
                                    System.out.println("Client - " + str);
                                    //если принята строка /end
                                    if (str.equals("/end")){
                                        //отправляем сообщение только тому,кто прислал /end
                                        //server.broadcastMsgClosed(socket); //так слишком сложно - мы же в нужном нам потоке
                                        out.writeUTF("/server Cloused");  //отправляем сообщение только тому,кто прислал /end
                                        // а удаление слиента делаем в конце блока finally вызовом метода server.unsubscribe()
                                        break; //выходим из бесконечного цикла
                                    }

                                if (str.startsWith("/w")){
                                    String[] tokens = str.split(" ", 3);  //массив из 3 элементов
                                    String nick = tokens[1];
                                    String msg = tokens[2];
                                    server.broadcastPersonalMsg(nick, msg, ClientHandler.this);

                                }else if (str.startsWith("/blacklist")) {
                                    String[] tokens = str.split(" ");
                                    //blackList.add(tokens[1]);
                                    Auth_DB_Service.addNickToBlacklist(nick,tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                    blackList =  Auth_DB_Service.getNicksFromBlacklist(nick);
                                    String s = getBlacklistString(blackList);
                                    //sendMsg("В чёрном списке: " + s);
                                    System.out.println(nick + " имеет в  чёрном списке: " + s);
                                }else {
                                    // чтобы легче ориентироваться в чёрных списках
                                    blackList =  Auth_DB_Service.getNicksFromBlacklist(nick);
                                    String s = getBlacklistString(blackList);
                                    System.out.println(nick + " имеет в  чёрном списке: " + s);
                                    //отправляем сообщение всем, кто в списке Vector<ClientHandler> clients
                                    server.broadcastMsg(ClientHandler.this, nick + " : " + str); //ни фига себе !
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            server.unsubscribe(ClientHandler.this, nick);
                        }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String str){
        try {
            out.writeUTF(str);  //отправляем сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgClosed(){
        try {
            out.writeUTF("/server Cloused");  //отправляем сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgStopped(String nick){
        try {
            out.writeUTF( nick + " вышел из чата");  //отправляем сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgBegin(String nick){
        try {
            out.writeUTF(nick + " подключился к чату");  //отправляем сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBlacklistString(List<String> blackList ){
        String s = "";
        for (int i = 0; i<blackList.size(); i++){
            s += blackList.get(i) + " ";
        }
        return s;
    }
}
