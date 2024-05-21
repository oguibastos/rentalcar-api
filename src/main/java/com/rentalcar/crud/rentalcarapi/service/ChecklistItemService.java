package com.rentalcar.crud.rentalcarapi.service;

import com.rentalcar.crud.rentalcarapi.entity.CategoryEntity;
import com.rentalcar.crud.rentalcarapi.entity.ChecklistItemEntity;
import com.rentalcar.crud.rentalcarapi.exception.ResourceNotFoundException;
import com.rentalcar.crud.rentalcarapi.repository.CategoryRepository;
import com.rentalcar.crud.rentalcarapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class ChecklistItemService {

    @Autowired
    private ChecklistItemRepository checklistItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private void validateChecklistItemData(String description, Boolean isCompleted, LocalDate deadline, String categoryGuid) {

        if(!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Checklist item must have a description");
        }

        if(!StringUtils.hasText(categoryGuid)) {
            throw new IllegalArgumentException("Checklist category guid must be provided");
        }

        if(isCompleted == null) {
            throw new IllegalArgumentException("Checklist item must have a flag indicating if it is completed or not");
        }

        if(deadline == null) {
            throw new IllegalArgumentException("Checklist item must have a deadline");
        }
    }

    public ChecklistItemEntity updateChecklistItem(String guid, String description, Boolean isCompleted, LocalDate deadline, String categoryGuid) {

        if(!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }

        ChecklistItemEntity retrievedItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("ChecklistItem not found.")
        );

        if(StringUtils.hasText(description)) {
            retrievedItem.setDescription(description);
        }

        if(isCompleted != null) {
            retrievedItem.setIsComplete(isCompleted);
        }

        if(deadline != null) {
            retrievedItem.setDeadline(deadline);
        }

        if(categoryGuid != null) {
            CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(
                    () -> new ResourceNotFoundException("Category not found."));
            retrievedItem.setCategory(retrievedCategory);
        }

        log.debug("Updating checklist item [ checklistItem = {} ]", retrievedItem.toString());

        return this.checklistItemRepository.save(retrievedItem);
    }

    public ChecklistItemEntity addNewChecklistItem(String description, Boolean isCompleted, LocalDate deadline, String categoryGuid) {
        this.validateChecklistItemData(description, isCompleted, deadline, categoryGuid);

        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found."));

        ChecklistItemEntity checklistItemEntity = new ChecklistItemEntity();
        checklistItemEntity.setGuid(UUID.randomUUID().toString());
        checklistItemEntity.setDescription(description);
        checklistItemEntity.setDeadline(deadline);
        checklistItemEntity.setPostedDate(LocalDate.now());
        checklistItemEntity.setCategory(retrievedCategory);

        log.debug("Adding new checklist item [ checklistItem = {} ]", checklistItemEntity);

        return checklistItemRepository.save(checklistItemEntity);
    }

    public Iterable<ChecklistItemEntity> findAllChecklistItems() {
        return this.checklistItemRepository.findAll();
    }

    public void deleteChecklistItem(String guid) {
        if(!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Guid cannot be null or empty");
        }
        ChecklistItemEntity retrievedItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("ChecklistItem not found.")
        );

        log.debug("Deleting checklist item [ guid = {} ]", guid);

        this.checklistItemRepository.delete(retrievedItem);
    }

    public ChecklistItemEntity findChecklistItemByGuid(String guid) {

        if(!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid be empty or null");
        }

        return this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("ChecklistItem not found.")
        );
    }
}

