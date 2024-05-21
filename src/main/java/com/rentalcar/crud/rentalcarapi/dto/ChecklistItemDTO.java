package com.rentalcar.crud.rentalcarapi.dto;

import com.rentalcar.crud.rentalcarapi.entity.ChecklistItemEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ChecklistItemDTO {

    private String guid;

    private String description;

    private Boolean isCompleted;

    private LocalDate deadline;

    private LocalDate postDate;

    private String category;

    public static ChecklistItemDTO toDTO(ChecklistItemEntity checklistItemEntity) {

        return ChecklistItemDTO.builder()
                .guid(checklistItemEntity.getGuid())
                .description(checklistItemEntity.getDescription())
                .deadline(checklistItemEntity.getDeadline())
                .postDate(checklistItemEntity.getPostedDate())
                .isCompleted(checklistItemEntity.getIsCompleted())
                .category(checklistItemEntity.getCategory().getGuid())
                .build();
    }
}
