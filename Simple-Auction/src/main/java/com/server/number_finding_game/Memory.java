package com.server.number_finding_game;

import com.DTO.UsersDTO;

/**
 * Information data storage
 * @author HiamKaito
 */
public class Memory {
    public static NewClient client = new NewClient();

    public static UsersDTO usersDTO = new UsersDTO();


//    ======================================================================
//    ======================================================================

    //    About Client to CLien
    public static String messenger = " ";
    /**
     * true if there is a messenger come from the other client
     * <br>It mean in your turn is false
     */
    static boolean playerMessenger = true;

    static String statusMessenger = " ";
}
