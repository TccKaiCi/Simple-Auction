package com.DAO;

import com.DTO.AuctionsDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class AuctionsDAO {
    MyConnectUnit connect;
    String strTableName = "tblauctions";

    /**
     * Lấy thông tin từ Database
     */
    public ArrayList<AuctionsDTO> readDB(String condition, String orderBy) throws Exception {
        // kết nối CSDL
        connect = new MyConnectUnit();

        ResultSet result = this.connect.Select(strTableName, condition, orderBy);
        ArrayList<AuctionsDTO> DTOs = new ArrayList<>();
        while (result.next()) {
            AuctionsDTO DTO = new AuctionsDTO();

            DTO.setStrProductName(result.getString("productName"));
            DTO.setIntPurchasePrice(result.getInt("purchasePrice"));
            DTO.setStrUserName(result.getString("userName"));


            DTOs.add(DTO);
        }
        connect.Close();
        return DTOs;
    }

    public ArrayList<AuctionsDTO> readDB(String condition) throws Exception {
        return readDB(condition, null);
    }

    public ArrayList<AuctionsDTO> readDB() throws Exception {
        return readDB(null);
    }

    public Boolean add(AuctionsDTO tk) throws Exception {
        connect = new MyConnectUnit();

        // tạo đối tượng truyền vào
        HashMap<String, Object> insertValues = new HashMap<>();
        insertValues.put("productName", tk.getStrProductName());
        insertValues.put("purchasePrice ", tk.getIntPurchasePrice());
        insertValues.put("userName ", tk.getStrUserName());

        Boolean check = connect.Insert(strTableName, insertValues);

        connect.Close();
        return check;
    }
}
