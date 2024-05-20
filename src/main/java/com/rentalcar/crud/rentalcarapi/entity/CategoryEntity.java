package com.rentalcar.crud.rentalcarapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity(name = "Category")
@Table(indexes = { @Index(name = "IDX_GUID_CAT", columnList = "guid")})
public class CategoryEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true)
    private String name;

    //Para mapear os relacionamentos | um para muitos
    //Uma categoria está em muitos checklistItems e um checklistItems tem uma categoria
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ChecklistItemEntity> checklistItems;
}
