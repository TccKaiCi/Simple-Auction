package com.BUS;

import com.DAO.ProductsDAO;
import com.DTO.ProductsDTO;

import java.util.ArrayList;

/**
 * Only for server
 */
public class ProductsBUS {
    private ArrayList<ProductsDTO> list_DTO;
    /**
     * Xử lý các lệnh trong SQL
     */
    private final ProductsDAO DAO;

    public ProductsBUS() throws Exception {
        list_DTO = new ArrayList<>();
        DAO = new ProductsDAO();
        list_DTO = DAO.readDB();
    }

    /**
     * thêm 1 tài khoản vào danh sách và database
     *
     * @return true nếu thành công
     */
    public Boolean add(ProductsDTO DTO) throws Exception {
        if (DAO.add(DTO)) {
            list_DTO.add(DTO);
        }
        return false;
    }


    /**
     * Get data by Json
     * @return
     */
//    public String initJsonRankTable() {
//        try {
//            UsersBUS bus = new UsersBUS();
//            StringBuilder sb = new StringBuilder();
//
////        open json
//            sb.append("{\n" +
//                    "  \"data\": [");
//
//            list_DTO.forEach(model -> {
//                sb.append("{\n" +
//                        "      \"strUID\": " + model.getStrUid() + " ,\n" +
//                        "      \"intPoint\": " + model.getIntPoint() + ",\n" +
//                        "      \"strKetQua\": " + model.getStrKetQua() + ",\n" +
//                        "      \"strNameInfor\": " + bus.getUserAccountByUserName_PassWord(model.getStrUid()) + "\n" +
//                        "    },");
//            });
//
////        delete end ","
//            sb.deleteCharAt(sb.length() - 1);
////        close json
//            sb.append("\n" +
//                    " \n]" +
//                    "}");
//
//            return sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

}
