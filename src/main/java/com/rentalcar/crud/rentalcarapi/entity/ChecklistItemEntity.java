package com.rentalcar.crud.rentalcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistItemEntity extends BaseEntity{

    private Long checklistItemId;

    private String description;

    private Boolean isComplete;

    private LocalTime deadline;

    private LocalTime postedDate;

    private CategoryEntity category;
}
