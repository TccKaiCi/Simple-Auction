package com.client.number_finding_game.GUI;

import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomController implements Initializable {
    @FXML
    private Button btn_quit, btn_bid;
    @FXML
    Label lbl_name, lbl_bal;
    @FXML
    TextField tf_bid;


    private static final DropShadow hoverEffect = new DropShadow();
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #A7DA46; ";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4E9525; ";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_quit.setOnAction(this::setBtn_quit);
        btn_bid.setOnAction(this::setBtn_bid);

        setHoverEffect();
        Node[] node = {btn_bid, btn_quit};
        setButtonAnimate(node);

        lbl_name.setText(Memory.usersDTO.getStrUserName());
        lbl_bal.setText(String.valueOf(Memory.usersDTO.getIntBalance()));

        // force the field to be numeric only
        tf_bid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_bid.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void setHoverEffect() {
        hoverEffect.setColor(Color.web("#FFE8D6"));
        hoverEffect.setRadius(35);
        hoverEffect.setWidth(40);
        hoverEffect.setHeight(40);
    }

    public void setButtonAnimate(Node[] node) {
        for (Node item : node) {
            item.setOnMouseEntered(mouseEvent -> {
                item.setEffect(hoverEffect);
                item.setStyle(HOVERED_BUTTON_STYLE);
            });
            item.setOnMouseExited(mouseEvent -> {
                item.setEffect(null);
                item.setStyle(IDLE_BUTTON_STYLE);
            });
            item.setOnMousePressed(mouseEvent -> item.setStyle(HOVERED_BUTTON_STYLE));
            item.setOnMouseReleased(mouseEvent -> item.setStyle(IDLE_BUTTON_STYLE));
        }
    }

    public void setBtn_bid(ActionEvent event) {
    }

    public void setBtn_quit(ActionEvent event) {
        System.exit(0);
    }
}
