package com.rentalcar.crud.rentalcarapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Data
@Entity(name = "ChecklistItem")
@Table(indexes = { @Index(name = "IDX_GUID_CK_IT", columnList = "guid")})
public class ChecklistItemEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistItemId;

    private String description;

    private Boolean isComplete;

    private LocalTime deadline;

    private LocalTime postedDate;

    @ManyToOne
    private CategoryEntity category;
}
