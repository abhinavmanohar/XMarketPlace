package com.xmarketplace.Mapper;



import com.xmarketplace.DTO.ProductListingDTO;
import com.xmarketplace.Entity.ProductListing;

import java.util.ArrayList;
import java.util.List;

public class ProductListingMapper {

    public static ProductListing dtoToEntity(ProductListingDTO dto) {
        ProductListing entity = new ProductListing();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public static ProductListingDTO entityToDto(ProductListing entity) {
        ProductListingDTO dto = new ProductListingDTO(entity.getName(), entity.getPrice(), entity.getQuantity(), entity.getId());
        return dto;
    }
    public static List<ProductListingDTO> entityToDto(List<ProductListing> entity) {
        List<ProductListingDTO> productListingDTOList = new ArrayList<>();
        entity.stream().forEach(e -> {
            ProductListingDTO dto = new ProductListingDTO(e.getName(), e.getPrice(), e.getQuantity(),e.getId());
            productListingDTOList.add(dto);
        });
        return productListingDTOList;
    }
}