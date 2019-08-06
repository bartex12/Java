package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Server server;
    private String nick;

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler(Server server, Socket socket){

        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                        try {

//                            while (true){
//                                String str = in.readUTF();  //принимаем сообщение
//                                if (str.startsWith("/auth")){
//                                    String[] tokens = str.split(" ");
//                                    String newNick = AuthService.getNickByLoginAndPass(tokens[1],tokens[2]);
//                                    if (newNick!=null){
//                                        nick = newNick;
//                                        //добавляем участника в список участников и сообщаем о новом участнике чата
//                                        server.subscribe(ClientHandler.this);
//                                        //server.broadcastMsgBegin(ClientHandler.this);
//                                        break;
//                                    }else {
//                                        sendMsg(" Неверный логин/пароль ");
//                                    }
//                                }
//                            }


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
                                    //отправляем сообщение всем, кто в списке Vector<ClientHandler> clients
                                    server.broadcastMsg(str);
                            }
                        }catch (IOException e){
                            e.printStackTrace();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
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
                            server.unsubscribe(ClientHandler.this);
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

    public void sendMsgStopped(){
        try {
            out.writeUTF( " Участник вышел из чата");  //отправляем сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgBegin(){
        try {
            out.writeUTF(" Новый участник подключился к чату");  //отправляем сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}