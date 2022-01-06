package com.DAO;

import com.DTO.UsersDTO;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UsersDAO {
    MyConnectUnit connect;
    String strTableName = "tblusers";

    /**
     * Lấy thông tin từ Database
     */
    public ArrayList<UsersDTO> readDB(String condition, String orderBy) throws Exception {
        // kết nối CSDL
        connect = new MyConnectUnit();

        ResultSet result = this.connect.Select(strTableName, condition, orderBy);
        ArrayList<UsersDTO> DTOs = new ArrayList<>();
        while (result.next()) {
            UsersDTO dto = new UsersDTO();

            dto.setStrUserName(result.getString("userName"));
            dto.setStrHashPassWord(result.getString("hashPassword"));
            dto.setIntBalance(result.getInt("balance"));
            dto.setBoolLockStatus(result.getBoolean("lockStatus"));

            DTOs.add(dto);
        }
        connect.Close();
        return DTOs;
    }

    public ArrayList<UsersDTO> readDB(String condition) throws Exception {
        return readDB(condition, null);
    }

    public ArrayList<UsersDTO> readDB() throws Exception {
        return readDB(null);
    }
}
