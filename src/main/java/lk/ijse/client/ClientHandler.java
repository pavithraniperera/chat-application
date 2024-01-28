package lk.ijse.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler {
    private Socket socket ;
    private  List<ClientHandler> clients ;
    private DataInputStream dataInputStream ;
    private DataOutputStream dataOutputStream ;
    private String msg = " ";

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.clients = clients;
        this.socket = socket;
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sentMessage();
    }

    private void sentMessage() {
        new Thread(()->{
            while (socket.isConnected()){
                try {
                    msg = dataInputStream.readUTF();
                    if (msg.startsWith("IMAGE IS MESSAGE")){
                        handleImageMsg(msg);

                    }else {
                        for (ClientHandler clientHandler : clients) {
                            if (clientHandler.socket.getPort() != socket.getPort()) {
                                clientHandler.dataOutputStream.writeUTF(msg);
                                clientHandler.dataOutputStream.flush();
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }
    public void handleImageMsg(String msg){
        try {
            int imageDataLength =dataInputStream.readInt();
            byte[] imageData = new byte[imageDataLength];
            dataInputStream.readFully(imageData);
            // Broadcast the image to other clients
            broadcastImage(imageData,msg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void broadcastImage(byte[] imageData, String msg) {
        String clientName = msg.split("-")[1];
        for (ClientHandler clientHandler : clients){
            if (clientHandler.socket.getPort()!=socket.getPort()){
                try {
                    clientHandler.dataOutputStream.writeUTF("IMAGE IS MESSAGE"+"-"+clientName);
                    clientHandler.dataOutputStream.flush();
                    clientHandler.dataOutputStream.writeInt(imageData.length);
                    clientHandler.dataOutputStream.flush();
                    clientHandler.dataOutputStream.write(imageData);
                    clientHandler.dataOutputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }



}