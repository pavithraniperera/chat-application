package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class ClientFormController {

    public Label lblName;
    public ImageView imgExit;
    public ImageView imgMinimize;
   // public TextArea txtArea;
    public TextField txtMsg;
    public JFXButton btnSend;
    public ImageView imgUpload;
    public VBox vboxMsg;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName ="";
    public void initialize(){
        displayMsg();

    }

    private void displayMsg() {
        new Thread(()->{ try {
            socket = new Socket("localhost",3001);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("[Client connected]");
            while (socket.isConnected()){
                String receivingMsg = dataInputStream.readUTF();
                receiveMessage(receivingMsg,vboxMsg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }).start();


    }

    private void receiveMessage(String receivingMsg, VBox vboxMsg) {
        System.out.println(receivingMsg);
        if (receivingMsg.startsWith("IMAGE IS MESSAGE")){

            receiveImageMessage(receivingMsg);
            
        }else {
            receiveTextMessage(receivingMsg,vboxMsg);
        }
        

    }

    private void receiveImageMessage(String receivingMsg) {
        try {
            String name = receivingMsg.split("-")[1];
            int imageDataLength = dataInputStream.readInt();
            byte[] imageData = new byte[imageDataLength];
            dataInputStream.readFully(imageData);
            saveImageToFile(imageData);
            displayReceivedImage(imageData,name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void saveImageToFile(byte[] imageData) {
        try {
            // Save the image data to a temporary file (you can customize the file path)
            Path imagePath = Files.createTempFile("received_image", ".png");
            Files.write(imagePath, imageData, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveTextMessage(String receivingMsg,VBox vboxMsg){
        String name = receivingMsg.split("-")[0];
        String serverMsg = receivingMsg.split("-")[1];
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        HBox hBoxName = new HBox();
        hBoxName.setAlignment(Pos.CENTER_LEFT);
        javafx.scene.text.Text textName = new Text(name);
        TextFlow textFlow = new TextFlow(textName);
        hBoxName.getChildren().add(textFlow);

        Text text = new Text(serverMsg);
        TextFlow textFlowMsg= new TextFlow(text);
        textFlowMsg.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
        text.setStyle("-fx-font-size: 16");
        textFlowMsg.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0,0,0));
        hBox.getChildren().add(textFlowMsg);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vboxMsg.getChildren().add(hBoxName);
                vboxMsg.getChildren().add(hBox);
            }
        });
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        sendMsg(txtMsg.getText());
    }

    private void sendMsg(String msgToSend) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 0, 10));

        Text text = new Text(msgToSend);
        // Example: Set font to Noto Emoji
        text.setFont(Font.font("Noto Emoji", FontWeight.NORMAL, FontPosture.REGULAR, 16));

        text.setStyle("-fx-font-size: 16");
        TextFlow textFlow = new TextFlow(text);

//              #0693e3 #37d67a #40bf75
        textFlow.setStyle("-fx-background-color: #7164cb; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1));

        hBox.getChildren().add(textFlow);

        HBox hBoxTime = new HBox();
        hBoxTime.setAlignment(Pos.CENTER_RIGHT);
        hBoxTime.setPadding(new Insets(0, 5, 5, 10));
        String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Text time = new Text(stringTime);
        time.setStyle("-fx-font-size: 10");

        hBoxTime.getChildren().add(time);

        vboxMsg.getChildren().add(hBox);
        vboxMsg.getChildren().add(hBoxTime);


        try {
            dataOutputStream.writeUTF(getClientName() + "-" + msgToSend);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        txtMsg.clear();

    }

    public void imgMinimizeOnAction(MouseEvent mouseEvent) {
        // Get the Stage from any UI component
        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        // Minimize the stage
        stage.setIconified(true);

    }

    public void imgExitOnAction(MouseEvent mouseEvent) {
        // Get the Stage from any UI component
        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        closeSocket();

        // Close the stage (window)
        stage.close();

    }

    private void closeSocket() {  try {
        if (socket != null && !socket.isClosed()) {
            // Close the socket
            socket.close();
            System.out.println("[Socket closed]");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void imgUploadOnAction(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) vboxMsg.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Process the selected image file
            sendImageToServer(selectedFile);
        }

    }

    private void sendImageToServer(File selectedFile) {
        // Read the image file into a byte array
        byte[] imageData = new byte[0];
        try {
            imageData = Files.readAllBytes(selectedFile.toPath());
            // Encode the image data as Base64 for sending
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            // Send the image to the server
            dataOutputStream.writeUTF("IMAGE IS MESSAGE"+"-"+getClientName());
            dataOutputStream.flush();
            dataOutputStream.writeInt(imageData.length);
            dataOutputStream.flush();
            dataOutputStream.write(imageData);
            dataOutputStream.flush();

            // Display the image in the UI
            displayImage(imageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void displayImage(byte[] imageData) {


        Image image = new Image(new ByteArrayInputStream(imageData));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        HBox hBox = new HBox(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 0, 10));
       // hBox.setStyle("-fx-background-color: #7164cb; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");



        HBox hBoxTime = new HBox();
        hBoxTime.setAlignment(Pos.CENTER_RIGHT);
        hBoxTime.setPadding(new Insets(0, 5, 5, 10));
        String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Text time = new Text(stringTime);
        time.setStyle("-fx-font-size: 10");

        hBoxTime.getChildren().add(time);

        vboxMsg.getChildren().add(hBox);
        vboxMsg.getChildren().add(hBoxTime);

        // Add the ImageView to your UI (e.g., to a VBox)

    }
    public void displayReceivedImage(byte[] imageData, String name){
        Image image = new Image(new ByteArrayInputStream(imageData));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        HBox hBox = new HBox(imageView);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 0, 10));

        HBox hBoxName = new HBox();
        hBoxName.setAlignment(Pos.CENTER_LEFT);
        javafx.scene.text.Text textName = new Text(name);
        TextFlow textFlow = new TextFlow(textName);
        hBoxName.getChildren().add(textFlow);
       // hBox.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vboxMsg.getChildren().add(hBoxName);
                vboxMsg.getChildren().add(hBox);
            }
        });

    }



    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
       this. clientName = clientName;
        lblName.setText(clientName);
    }
}
