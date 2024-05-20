package com.rentalcar.crud.rentalcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity{

    private Long categoryId;

    private String name;

    //Para mapear os relacionamentos | um para muitos
    //Uma categoria está em muitos checklistItems e um checklistItems tem uma categoria
    private List<ChecklistItemEntity> checklistItems;

}
