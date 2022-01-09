package com.server.number_finding_game;

import com.BUS.UsersBUS;
import com.DTO.UsersDTO;
import com.server.number_finding_game.GUI.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class NewServer implements Runnable {
    public int softLimit = 40;
    public int hardLimit = 45;

    private final int port = 8081;
    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private final ChatServerThread[] clients = new ChatServerThread[hardLimit];

    public int intTime = 100;
    private int clientCount = 0;

    // for user bid
    public int bidTemp = 0;
    public String userBid = "";

    public HashMap<String, String> userStatus = new HashMap<>();
    public static UsersBUS listUsers;

    public static void main(String[] args) {
        NewServer news = new NewServer();
    }

    public NewServer() {
        try {
            serverSocket = new ServerSocket(port);
            // khởi tạo
            listUsers = new UsersBUS();
            System.out.println("Server started on port " + serverSocket.getLocalPort() + "...");
            System.out.println("Waiting for client...");
            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            System.out.println("Can not bind to port : " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                // wait until client socket connecting, then add new thread
                addThreadClient(serverSocket.accept());
            } catch (IOException e) {
                System.out.println("Server accept error : " + e);
                stop();
            }
        }
    }

    public void stop() {
        if (thread != null) {
            thread = null;
        }
    }

    private int findClient(SocketAddress ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void handle(SocketAddress ID, String input) throws Exception {
        if (clientCount > softLimit) {
            clients[findClient(ID)].send("Server is very busy now");
            clients[findClient(ID)].send("exit");
            remove(ID);
            return;
        }
        // khởi tạo

        System.out.println("Server get from Client " + ID + " " + input);

        if (input.equals("exit")) {
            clients[findClient(ID)].send("exit");
            remove(ID);
        } else {
            //Xử lí cú pháp
            if (input.contains(";")) {
                String[] inputMessenger = input.split(";");

                switch (inputMessenger[0]) {
                    //SIGNIN;tendangnhap;matkhau
                    case "SIGNIN":
                        UsersDTO dtotmp = new UsersDTO();

                        dtotmp.setStrUserName(inputMessenger[1]);
                        dtotmp.setStrHashPassWord("Auction" + inputMessenger[2] + "LTM");

                        // Mã hóa dữ liệu
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] encodedhash = digest.digest(
                                dtotmp.getStrHashPassWord().getBytes(StandardCharsets.UTF_8));
                        // gán lại kết quả mã hóa
                        dtotmp.setStrHashPassWord(bytesToHex(encodedhash));

                        // kiểm tra tài khoản : username, password
                        if (listUsers.kiemTraDangNhap(dtotmp)) {
                            if (!listUsers.kiemTraTrangThai(dtotmp)) {
                                clients[findClient(ID)].send("Your account has been locked");
                            } else {
                                boolean valid = true;
                                dtotmp = listUsers.getUserAccountByUserName_PassWord(dtotmp);

                                clients[findClient(ID)].setUserName(dtotmp.getStrUserName());

                                for (int i = 0; i < clientCount; i++) {
                                    if (clients[i].getUserName().equalsIgnoreCase(dtotmp.getStrUserName())) {
                                        if (clients[i].getID() == ID) {
                                            continue;
                                        }
                                        clients[findClient(ID)].send("Account are sign in on other address");
                                        valid = false;
                                    }
                                }

                                if (valid) {
                                    userStatus.put(clients[findClient(ID)].getUserName(), "online");
                                    clients[findClient(ID)].send("valid user");

                                    // return for client all infor user

                                    String sendmess = "Account;" +
                                            dtotmp.getStrUserName() + ":" +
                                            dtotmp.getStrHashPassWord() + ":" +
                                            dtotmp.getIntBalance() + ":" +
                                            dtotmp.getBoolLockStatus();

                                    // set uid
                                    Thread.sleep(500);
                                    clients[findClient(ID)].send(sendmess);
                                    clients[findClient(ID)].setActive(true);
                                }
                            }
                        }
                        break;
                    case "UserBID":
                        //UserBID;USername;bid
                        if (bidTemp < Integer.parseInt(inputMessenger[2])) {

                            bidTemp = Integer.parseInt(inputMessenger[2]);
                            userBid = inputMessenger[1];

                            listUsers.lock_unclock_UserName(userBid);

                            ServerController.tempProduct.setBoolSoldStatus(true);
                        }
                        break;
                }
            }
        }
    }

    public void sendMessengerToAllInLobby(String input) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getActive()) {
                clients[i].send(input);
            }
        }
    }

    public synchronized void remove(SocketAddress ID) {
        if (findClient(ID) != -1) {
            userStatus.put(clients[findClient(ID)].getUserName(), "offline");
            clients[findClient(ID)].setActive(false);

            int index = findClient(ID);
            if (index >= 0) {
                ChatServerThread threadToTerminate = clients[index];
                System.out.println("Removing client thread " + ID + " at " + index);
                if (index < clientCount - 1) {
                    for (int i = index + 1; i < clientCount; i++) {
                        clients[i - 1] = clients[i];
                    }
                }
                clientCount--;
                try {
                    threadToTerminate.close();
                } catch (IOException e) {
                    System.out.println("Error closing thread : " + e.getMessage());
                }
            }
        }
    }

    private void addThreadClient(Socket socket) {
        if (clientCount < clients.length) {
            clients[clientCount] = new ChatServerThread(this, socket);
            clients[clientCount].start();
            clientCount++;
        } else {
            System.out.println("Client refused : maximum " + clients.length + " reached.");
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public void setIntTime(int intTime) {
        this.intTime = intTime;
    }
}
