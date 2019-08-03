package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server  {

    private Vector<ClientHandler> clients;
    Socket socket = null;

    public Server(){

        clients =  new Vector<>();
        ServerSocket server = null;


        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true){
                socket = server.accept();
                clients.add( new ClientHandler(this, socket));
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
