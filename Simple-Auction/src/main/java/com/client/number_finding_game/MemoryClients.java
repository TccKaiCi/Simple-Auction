package com.client.number_finding_game;

import com.DTO.ProductsDTO;
import com.DTO.UsersDTO;

/**
 * Information data storage
 * @author HiamKaito
 */
public class MemoryClients {
    public static NewClient client = new NewClient();

    public static UsersDTO usersDTO = new UsersDTO();
    public static ProductsDTO productsDTO = new ProductsDTO();
    public static int intTime = 0;

    /**
     * 0 is nothing happen <br>
     * 1 is Auction launch <br>
     * -1 is fail Auction <br>
     * 2 is show who is win.
     */
    public static int isAuctionStatus = 0;
    public static String whoWin = "";

//    ======================================================================
//    ======================================================================

    //    About Client to CLien
    public static String messenger = " ";

    static String statusMessenger = " ";
}
