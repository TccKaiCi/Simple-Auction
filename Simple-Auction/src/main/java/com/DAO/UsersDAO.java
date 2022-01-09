package com.DAO;

import com.DTO.UsersDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

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

    public Boolean update(UsersDTO tk) throws Exception {
        connect = new MyConnectUnit();

        // tạo đối tượng truyền vào
        HashMap<String, Object> insertValues = new HashMap<>();
        insertValues.put("userName", tk.getStrUserName());
        insertValues.put("balance", tk.getIntBalance());

        String condition = " userName = '" + tk.getStrUserName() + "'";

        Boolean check = connect.Update(strTableName, insertValues, condition);

        connect.Close();
        return check;
    }

    public ArrayList<UsersDTO> readDB(String condition) throws Exception {
        return readDB(condition, null);
    }

    public ArrayList<UsersDTO> readDB() throws Exception {
        return readDB(null);
    }
}
