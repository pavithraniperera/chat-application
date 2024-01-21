package lk.ijse.server;

import lk.ijse.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
     private static Server server;
     private ServerSocket serverSocket;
     private Socket socket;
     private List<ClientHandler> clients = new ArrayList<>();

     private Server() throws IOException {
         serverSocket = new ServerSocket(3001);
     }
     public static Server getInstance() throws IOException {
         return  server==null?server=new Server():server;
     }
     public void makeSocket(){
         while (!serverSocket.isClosed()){
             try {

                 socket=serverSocket.accept();
                 ClientHandler clientHandler = new ClientHandler(socket,clients);
                 clients.add(clientHandler);
                 System.out.println("Client Socket Accepted "+ socket.toString() );

             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }

     }
}
