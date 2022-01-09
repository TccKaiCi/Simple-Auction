package com.BUS;

import com.DAO.UsersDAO;
import com.DTO.ProductsDTO;
import com.DTO.UsersDTO;

import java.util.ArrayList;


public class UsersBUS {
    private ArrayList<UsersDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private final UsersDAO DAO;

    public UsersBUS() throws Exception {
        list_DTO = new ArrayList<>();
        DAO = new UsersDAO();
        list_DTO = DAO.readDB();
    }

    /**
     * Kiêm tra xem tài khoản đó có trong arraylist hay chưa <br>
     * <h3> phân biệt hoa thường </h3>
     *
     * @return true nếu thành công
     */
    public Boolean kiemTraDangNhap(UsersDTO DTO) {
        for (UsersDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())
                    && model.getStrHashPassWord().equals(DTO.getStrHashPassWord())) {
                return true;
            }
        }
        return false;
    }

    public Boolean kiemTraTrangThai(UsersDTO DTO) {
        for (UsersDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())
                    && model.getBoolLockStatus() == false) {
                return true;
            }
        }
        return false;
    }

    public UsersDTO getUserAccountByUserName_PassWord(UsersDTO DTO) {
        for (UsersDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(DTO.getStrUserName())
                    && model.getStrHashPassWord().equals(DTO.getStrHashPassWord())) {
                return model;
            }
        }
        return null;
    }

    public UsersDTO getUserAccountUserName(String userName) {
        for (UsersDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(userName)) {
                return model;
            }
        }
        return null;
    }

    public void lock_unclock_UserName(String userName) {
        for (UsersDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(userName)) {
                model.setBoolLockStatus(true);
            } else {
                model.setBoolLockStatus(false);
            }

        }
    }

    public int getUserMoney_UserName(String userName) {
        for (UsersDTO model : list_DTO) {
            // kiểm tra mật khảu và tên đăng nhập
            if (model.getStrUserName().equals(userName)) {
                return model.getIntBalance();
            }
        }
        return 0;
    }

    public Boolean update(UsersDTO DTO) throws Exception {
        if (DAO.update(DTO)) {
            for (UsersDTO dto : list_DTO) {
                if (dto.getStrUserName().equals(DTO.getStrUserName())) {
                    dto.setIntBalance(DTO.getIntBalance());
                    return true;
                }
            }
        }

        return false;
    }


}
