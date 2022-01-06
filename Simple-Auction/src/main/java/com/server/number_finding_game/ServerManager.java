package com.server.number_finding_game;

import java.net.SocketAddress;
import java.util.Scanner;

public class ServerManager {
    private static NewServer newServer;

    public static void main(String[] args) {
        newServer = new NewServer();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Status: Hien thi user status");
            System.out.println("Time: thoi gian");
            System.out.println("lobby");
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("status")) {
                displayStatus();
                s = scanner.nextLine();
            }
            //thoiGian
            if (s.equalsIgnoreCase("Time")) {
                int time;
                do {
                    System.out.println("thời gian: ");
                    s = scanner.nextLine();
                } while (!isInteger(s) && s != null);
                time = Integer.parseInt(s);

                newServer.setIntTime(time);
                System.out.println("Cài đặt thành công");
            }
        } while (true);
    }

    public static void displayStatus() {
        System.out.println("Status Account:\n");
        newServer.userStatus.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    public static boolean isInteger(String var) {
        try {
            Integer.parseInt(var);
            return true;

            // return false when "var" can't be converted to integer
            // NumberFormatException will return null
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
