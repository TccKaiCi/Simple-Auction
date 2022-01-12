package com.server.number_finding_game.GUI;


import com.BUS.AuctionsBUS;
import com.BUS.ProductsBUS;
import com.DTO.AuctionsDTO;
import com.DTO.ProductsDTO;
import com.DTO.UsersDTO;
import com.server.number_finding_game.NewServer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ServerController implements Initializable {
    @FXML
    TextField tf_numberUser, tf_Price, tf_Time;
    @FXML
    Button btn_quit, btn_Letgo;
    @FXML
    ComboBox cbb_Products;
    @FXML
    ImageView img;

    // for user
    @FXML
    Label lbl_high, lbl_high_1, lbl_high_2, lbl_high_3, lbl_high_4;

    private NewServer newServer;

    ProductsBUS listProducts;
    AuctionsBUS listAuctions;

    public static ProductsDTO tempProduct;
    String url = new File("").getAbsolutePath() + "\\src\\main\\resources\\com\\client\\number_finding_game\\assets\\Products\\";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // create
        newServer = new NewServer();
        try {
            listProducts = new ProductsBUS();
            listAuctions = new AuctionsBUS();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tempProduct = new ProductsDTO();

        // event UI
        btn_quit.setOnAction(this::setBtn_quit);
        btn_Letgo.setOnAction(this::setBtn_Letgo);
        cbb_Products.setOnAction(this::setCbb);

        // set combobox value
        setCbbValue();

        // for client
        Node[] node = {lbl_high, lbl_high_1, lbl_high_2, lbl_high_3, lbl_high_4};
        setVisible(node, false);

        // set time UI
        tf_Time.setText(String.valueOf(newServer.intTime));

        // force the field to be numeric only
        tf_Time.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_Time.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // set number player online display
        Timer countDown = new Timer();
        countDown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    int count = 0;
                    for (Map.Entry<String, String> entry : newServer.userStatus.entrySet()) {
                        if (entry.getValue().equals("online")) {
                            count++;
                        }
                    }

                    Node[] node = {lbl_high, lbl_high_1, lbl_high_2, lbl_high_3, lbl_high_4};

                    if (newServer.bidTemp != 0 && !newServer.userBid.equals("")) {
                        lbl_high_3.setText(newServer.userBid);
                        lbl_high_4.setText(String.valueOf(newServer.bidTemp));

                        setVisible(node, true);
                    } else {
                        setVisible(node, false);
                    }

                    tf_numberUser.setText(String.valueOf(count));

                });
            }
        }, 0, 1000);

    }

    public void setDisable(Node[] node, boolean value) {
        for (Node item : node) {
            item.setDisable(value);
        }
    }


    private void setBtn_Letgo(ActionEvent actionEvent) {
        newServer.setIntTime(Integer.parseInt(tf_Time.getText()));

        sendProduct_Client();

        Node[] node = {tf_Time, tf_Price, btn_quit, btn_Letgo, cbb_Products};
        setDisable(node, true);

        Timer countDown = new Timer();
        countDown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    newServer.intTime--;

                    if (newServer.intTime > 0) {
                        sendProduct_Client();
                        tf_Time.setText(String.valueOf(newServer.intTime));
                    } else {
                        // if no one bid product
                        if (!tempProduct.getBoolSoldStatus()) {
                            newServer.sendMessengerToAllInLobby("FailAuction;");
                        } else {
                            newServer.sendMessengerToAllInLobby(
                                    "UserWin;" +
                                            newServer.userBid + ";" +
                                            newServer.bidTemp
                            );

                            // lưu vào database cuộc đấu giá 1 sản phẩm
                            AuctionsDTO dto = new AuctionsDTO();
                            dto.setStrUserName(newServer.userBid);
                            dto.setStrProductName(tempProduct.getStrProductName());
                            dto.setIntPurchasePrice(newServer.bidTemp);

                            try {
                                listAuctions.add(dto);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //done
                            // cập nhật trạng thái sản phẩm
                            ProductsDTO product = new ProductsDTO();
                            try {
                                listProducts.update(tempProduct);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // done
                            // trừ tiền cho người mua
                            int temp = newServer.listUsers.getUserMoney_UserName(
                                    newServer.userBid
                            );

                            temp -= newServer.bidTemp;
                            UsersDTO usersDTO = new UsersDTO();
                            usersDTO.setStrUserName(newServer.userBid);
                            usersDTO.setIntBalance(temp);

                            try {
                                newServer.listUsers.update(usersDTO);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            newServer.listUsers.lock_unclock_UserName("");

                            // reset data
                            newServer.userBid = "";
                            newServer.bidTemp = 0;
                            tempProduct.setBoolSoldStatus(false);
                        }

                        cbb_Products.getItems().clear();
                        // set combobox value
                        setCbbValue();

                        countDown.cancel();
                        setDisable(node, false);
                    }

                });
            }
        }, 0, 1000);
    }

    public void sendProduct_Client() {
        String s = "Product;";

        s += cbb_Products.getValue() + ";";
        s += tf_Price.getText() + ";";
        s += newServer.intTime + ";";
        s += listProducts.getImageByProduct(String.valueOf(cbb_Products.getValue()));

        tempProduct.setStrProductName(String.valueOf(cbb_Products.getValue()));

        newServer.sendMessengerToAllInLobby(s);
    }

    public void setVisible(Node[] node, boolean value) {
        for (Node item : node) {
            item.setVisible(value);
        }
    }
    public void setCbbValue() {

        for (ProductsDTO dto : listProducts.getList_DTO()) {
            if (!dto.getBoolSoldStatus()) {
                cbb_Products.getItems().addAll(dto.getStrProductName());
            }
        }
        cbb_Products.getSelectionModel().select(0);

        // change image for value
        changeProductValue(String.valueOf(cbb_Products.getValue()));
    }

    // event change item in combobox
    private void setCbb(Event event) {
        changeProductValue(String.valueOf(cbb_Products.getValue()));
    }

    public void changeProductValue(String product) {
        tf_Price.setText(String.valueOf(listProducts.getValueByProduct(product)));

        // change image for value
        try {
            img.setImage(new Image(url +
                    listProducts.getImageByProduct(String.valueOf(product))
            ));
        } catch (Exception e) {
        }
    }


    public void setBtn_quit(ActionEvent event) {
        newServer.stop();
        System.exit(0);
    }
}
