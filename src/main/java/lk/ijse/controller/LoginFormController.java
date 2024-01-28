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
import lk.ijse.bo.ChatBo;
import lk.ijse.bo.ChatBoImpl;
import lk.ijse.dto.UserDto;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    private ChatBo chatBo = new ChatBoImpl();
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
            if(checkTheUserName()){
                saveToDatabase(txtUserName.getText());
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Check out your chats..").show();
            }

            txtUserName.clear();

        }else {
            new Alert(Alert.AlertType.ERROR, "Please enter your name").show();
        }

    }

    private boolean checkTheUserName() {
        UserDto dto = new UserDto(txtUserName.getText());
        try {
            return chatBo.checkTheUserName(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToDatabase(String name) {
        UserDto dto = new UserDto(txtUserName.getText());
        try {
            if(chatBo.saveUserData(dto)){
                new Alert(Alert.AlertType.CONFIRMATION,"WELCOME "+txtUserName.getText()+" to our chat!!!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
