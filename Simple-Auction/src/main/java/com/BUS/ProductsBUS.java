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

    public int getValueByProduct(String product) {
        for (ProductsDTO dto : list_DTO) {
            if (dto.getStrProductName().equals(product)) {
                return dto.getIntStartingPrice();
            }
        }
        return 0;
    }

    public String getImageByProduct(String product) {
        for (ProductsDTO dto : list_DTO) {
            if (dto.getStrProductName().equals(product)) {
                return dto.getStrImageUrl();
            }
        }
        return null;
    }


    public Boolean update(ProductsDTO DTO) throws Exception {
        if (DAO.update(DTO)) {
            for (ProductsDTO dto : list_DTO) {
                if (dto.getStrProductName().equals(DTO.getStrProductName())) {
                    dto.setBoolSoldStatus(true);
                    return true;
                }
            }
        }

        return false;
    }




    public ArrayList<ProductsDTO> getList_DTO() {
        return list_DTO;
    }

    public void setList_DTO(ArrayList<ProductsDTO> list_DTO) {
        this.list_DTO = list_DTO;
    }

}
