package com.client.number_finding_game.GUI;

import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button btn_Login;
    @FXML
    public TextField tf_username;
    @FXML
    public PasswordField pf_password;
    @FXML
    public Label lbl_Error, lbl_name, lbl_pass;

    public static final String IDLE_BUTTON_STYLE = "-fx-background-color: #A7DA46; ";
    public static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4E9525; ";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Memory.client.Connect();
        btn_Login.setOnAction(this::onClick);
        btn_Login.setOnMouseEntered(e -> btn_Login.setStyle(HOVERED_BUTTON_STYLE));
        btn_Login.setOnMouseExited(e -> btn_Login.setStyle(IDLE_BUTTON_STYLE));
    }

    public void onClick(ActionEvent event) {
        try {
            String SendingPack = "SIGNIN;" + tf_username.getText() + ";" + pf_password.getText();
            if (!pf_password.getText().equals("")) {
                Memory.client.sendMessenger(SendingPack);
                System.out.println(SendingPack);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Number finding game");
                stage.setResizable(false);
                //need add function prevent signin when password wrong
                if (Memory.messenger.equalsIgnoreCase("valid user")) {
                    while (!Memory.messenger.contains("Account")) {
                        System.out.println("");
                    }
                    FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
                    Parent root = fxmlLoader.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    if (Memory.messenger.contains("Account are sign in on other address")) {
                        lbl_Error.setText("Account are sign in on other device");
                    } else {
                        lbl_Error.setText("Wrong username or password");
                    }
                    setVi_TRUE_Dis_FALSE(lbl_Error);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVi_TRUE_Dis_FALSE(Node node) {
        node.setDisable(false);
        node.setVisible(true);
    }
}