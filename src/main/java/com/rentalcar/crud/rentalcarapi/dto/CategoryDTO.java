package com.rentalcar.crud.rentalcarapi.dto;

import com.rentalcar.crud.rentalcarapi.entity.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDTO {

    private String guid;

    private String name;

    public static CategoryDTO toDTO(CategoryEntity categoryEntity) {

        return CategoryDTO.builder()
                .guid(categoryEntity.getGuid())
                .name(categoryEntity.getName())
                .build();
    }
}
