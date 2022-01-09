package com.client.number_finding_game.GUI;

import com.client.number_finding_game.MemoryClients;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.*;

public class AuctionRoomController implements Initializable {
    @FXML
    private Button btn_quit, btn_bid;
    @FXML
    Label lbl_Result, lbl_name, lbl_bal, lbl_nameProduct, lbl_namePrice, lbl_nameTime, lbl_name1211, lbl_name12, lbl_name121;
    @FXML
    TextField tf_bid;
    @FXML
    ImageView productImage, gif, iconDola, iconDola_2;


    private static final NavigableMap<Integer, String> suffixes = new TreeMap<>();
    private static final DropShadow hoverEffect = new DropShadow();
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #A7DA46; ";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4E9525; ";

    String urlImage = new File("").getAbsolutePath() + "\\src\\main\\resources\\com\\client\\number_finding_game\\assets\\Products\\";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_quit.setOnAction(this::setBtn_quit);
        btn_bid.setOnAction(this::setBtn_bid);

        setHoverEffect();
        Node[] node = {btn_bid, btn_quit};
        setButtonAnimate(node);

        lbl_name.setText(MemoryClients.usersDTO.getStrUserName());

//        K, M, B, T, Q
//        thousand, million, billion.
        suffixes.put(1, "");
        suffixes.put(1000, "k");
        suffixes.put(1000000, "M");
        suffixes.put(1000000000, "B");

        lbl_bal.setText(
                formart(MemoryClients.usersDTO.getIntBalance())
        );

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


        Node[] nodePlay = {productImage, gif, lbl_name12, lbl_nameProduct, lbl_name121
                , iconDola, lbl_namePrice, lbl_name1211, lbl_nameTime
                , iconDola_2, tf_bid, btn_bid};

        Node[] nodeResult = {lbl_Result};

        // change value display
        Timer countDown = new Timer();
        countDown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    switch (MemoryClients.isAuctionStatus) {
                        case -1:
                            lbl_Result.setText("Auction Failed!");

                            setVisible(nodePlay, false);
                            setVisible(nodeResult, true);

                            break;
                        case 0:
                            lbl_Result.setText("Waitting For Auction!");

                            setVisible(nodePlay, false);
                            setVisible(nodeResult, true);

                            break;
                        case 1:
                            setVisible(nodePlay, true);
                            setVisible(nodeResult, false);


                            lbl_nameProduct.setText(MemoryClients.productsDTO.getStrProductName());
                            lbl_namePrice.setText(String.valueOf(MemoryClients.productsDTO.getIntStartingPrice()));
                            lbl_nameTime.setText(String.valueOf(MemoryClients.intTime));

                            productImage.setImage(new Image(urlImage + MemoryClients.productsDTO.getStrImageUrl()));

                            MemoryClients.isAuctionStatus = 0;
                            break;
                        case 2:
                            lbl_Result.setText(MemoryClients.whoWin);

                            setVisible(nodePlay, false);
                            setVisible(nodeResult, true);

                            String[] a = MemoryClients.whoWin.split(":");
                            if (MemoryClients.usersDTO.getStrUserName() == a[0]) {
                                int tempDelete = Integer.parseInt(a[2]);

                                MemoryClients.usersDTO.setIntBalance(MemoryClients.usersDTO.getIntBalance() - tempDelete);

                                lbl_bal.setText(
                                        formart(MemoryClients.usersDTO.getIntBalance())
                                );
                            }
                            break;
                    }
                });
            }
        }, 0, 1000);
    }

    public void setVisible(Node[] node, boolean value) {
        for (Node item : node) {
            item.setVisible(value);
        }
    }

    public static String formart(int value) {
        Map.Entry<Integer, String> e = suffixes.floorEntry(value);
        int divideBy = e.getKey();
        String suffix = e.getValue();

        return (value / divideBy) + suffix;
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
        int valueBid = Integer.parseInt(tf_bid.getText());
        if (valueBid >= MemoryClients.productsDTO.getIntStartingPrice()
                && valueBid <= MemoryClients.usersDTO.getIntBalance()) {
            MemoryClients.client.sendMessenger(
                    "UserBID" + ";" +
                            MemoryClients.usersDTO.getStrUserName() + ";" +
                            valueBid
            );
        }
    }

    public void setBtn_quit(ActionEvent event) {
        System.exit(0);
    }
}
