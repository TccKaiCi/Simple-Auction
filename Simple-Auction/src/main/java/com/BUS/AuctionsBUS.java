package com.BUS;

import com.DAO.AuctionsDAO;
import com.DTO.AuctionsDTO;

import java.util.ArrayList;

public class AuctionsBUS {
    private ArrayList<AuctionsDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private final AuctionsDAO DAO;

    public AuctionsBUS() throws Exception {
        list_DTO = new ArrayList<>();
        DAO = new AuctionsDAO();
        list_DTO = DAO.readDB();
    }

    public Boolean add(AuctionsDTO DTO) throws Exception {
        if (DAO.add(DTO)) {
            list_DTO.add(DTO);
        }
        return false;
    }





    public ArrayList<AuctionsDTO> getList_DTO() {
        return list_DTO;
    }

    public void setList_DTO(ArrayList<AuctionsDTO> list_DTO) {
        this.list_DTO = list_DTO;
    }
}
