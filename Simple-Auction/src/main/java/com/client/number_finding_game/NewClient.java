package com.client.number_finding_game;

import java.io.*;
import java.net.Socket;

// Client for Server4
public class NewClient implements Runnable {
    private final String serverName = "localhost";
    private final int serverPort = 8081;
    private Socket socket = null;
    private Thread thread = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private ChatClientThread client = null;

    public static void main(String[] args) {
        NewClient client = new NewClient();
        client.Connect();
    }


    //    is connect tho server???
    public boolean Connect() {
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Client started on port " + socket.getLocalPort() + "...");
            System.out.println("Connected to server " + socket.getRemoteSocketAddress());
            dis = new DataInputStream(System.in);
            dos = new DataOutputStream(socket.getOutputStream());
            client = new ChatClientThread(this, socket);
            thread = new Thread(this);
            thread.start();
            return true;
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                System.out.print("Message to server : ");
                dos.writeUTF(dis.readLine());
                dos.flush();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Error : " + e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Sending error : " + e.getMessage());
                stop();
            }
        }
    }

    //    Other client send messenger to here
    public void handleMessage(String message) {

        if (message.equals("exit")) {
            stop();
        } else {
            if ((!message.equalsIgnoreCase(" "))) {
//            say that we have a messenger
                MemoryClients.messenger = message;
                System.out.println("Client " + socket.getLocalPort() + " nhan duoc " + MemoryClients.messenger);
                if (message.contains(";")) {
                    String[] inputMessenger = message.split(";");
//                            xu ly tac vu
                    switch (inputMessenger[0]) {
                        case "Account":
                            String[] arr = inputMessenger[1].split(":");
                            MemoryClients.usersDTO.setStrUserName(arr[0]);
                            MemoryClients.usersDTO.setStrHashPassWord(arr[1]);
                            MemoryClients.usersDTO.setIntBalance(Integer.parseInt(arr[2]));
                            MemoryClients.usersDTO.setBoolLockStatus(Boolean.valueOf(arr[3]));
                            break;
                        case "Product":
                            //Product;The Elder Wand;300;100;/res/aaa/aa
                            MemoryClients.productsDTO.setStrProductName(inputMessenger[1]);
                            MemoryClients.productsDTO.setIntStartingPrice(Integer.parseInt(inputMessenger[2]));
                            MemoryClients.intTime = Integer.parseInt(inputMessenger[3]);
                            MemoryClients.productsDTO.setStrImageUrl(inputMessenger[4]);

                            MemoryClients.isAuctionStatus = 1;

                            break;
                        case "FailAuction":
                            MemoryClients.isAuctionStatus = -1;
                            break;
                        case "UserWin":
                            //UserWin;User_3;999999
                            MemoryClients.isAuctionStatus = 2;
                            MemoryClients.whoWin = inputMessenger[1] + ": is winner \n with bid is :" + inputMessenger[2];
                            break;
                    }
                }
            }
        }
    }


    //    Stop the connection
    public void stop() {
        try {
            thread = null;
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing : " + e.getMessage());
        }
        client.close();
    }

    public String getServer() {
        return String.valueOf(socket.getRemoteSocketAddress());
    }

    //    Try to find other servver
    public boolean findServer() {
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Client started on port " + socket.getLocalPort() + "...");
            System.out.println("Connected to server " + socket.getRemoteSocketAddress());
            System.out.println("Disconnect to server");
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void sendMessenger(String line) {
        MemoryClients.messenger = line;
        try {
            dos.writeUTF(MemoryClients.messenger);
            dos.flush();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error : " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Sending error : " + e.getMessage());
            stop();
        }
    }

}
