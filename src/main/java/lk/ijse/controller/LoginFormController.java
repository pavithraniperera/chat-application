package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.server.Server;

import java.io.IOException;

public class LoginFormController {
    @FXML
    private JFXButton btnLogin;

    @FXML
    private TextField txtUserName;
    /*public void initialize(){
        new Thread(()->{
            try {
                Server server = Server.getInstance();
                server.makeSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }*/

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtUserName.getText().isEmpty()&&txtUserName.getText().matches("[A-Za-z0-9]+")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Client_form.fxml"));
            Parent root = loader.load();
            Stage clientStage = new Stage();
            clientStage.setResizable(false);
            clientStage.setTitle("Client Window");
            Scene scene = new Scene(root);
            clientStage.setScene(scene);
            ClientFormController controller =loader.getController();
            controller.setClientName(txtUserName.getText());
            clientStage.show();
            txtUserName.clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please enter your name").show();
        }

    }
}
