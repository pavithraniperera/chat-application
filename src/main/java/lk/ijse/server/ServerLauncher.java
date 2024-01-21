package lk.ijse.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerLauncher extends Application {
    public void init(){
            new Thread(()->{
                try {
                    Server server = Server.getInstance();
                    System.out.println("Server started....");
                    server.makeSocket();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();


    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/Login_form.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Client");

        stage.show();

    }
}
