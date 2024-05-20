package com.rentalcar.crud.rentalcarapi.service;

import com.rentalcar.crud.rentalcarapi.entity.CategoryEntity;
import com.rentalcar.crud.rentalcarapi.entity.ChecklistItemEntity;
import com.rentalcar.crud.rentalcarapi.exception.CategoryServiceException;
import com.rentalcar.crud.rentalcarapi.exception.ResourceNotFoundException;
import com.rentalcar.crud.rentalcarapi.repository.CategoryRepository;
import com.rentalcar.crud.rentalcarapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CategoryService {

    ChecklistItemRepository checklistItemRepository;
    CategoryRepository categoryRepository;

    //Não é mais necessário utilizar o @AutoWired, a injeção é feita no construtor, mostrando o que
    //ele espera receber nas variáveis de referência
    public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity addNewCategory(String name) {

        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Category name be empty or null");
        }

        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setGuid(UUID.randomUUID().toString());
        newCategory.setName(name);

        log.debug("Adding a new category with name [ name = {} ]", name);

        return this.categoryRepository.save(newCategory);
    }

    public CategoryEntity updateCategory(String guid, String name) {
        if(guid == null || !StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Invalid parameters provided to update a Category");
        }

        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found.")
        );

        retrievedCategory.setName(name);
        log.debug("Updating category [ guid = {}, newName = {} ]", guid, name);

        return this.categoryRepository.save(retrievedCategory);
    }

    public void deleteCategory(String guid) {

        if(!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid be empty or null");
        }

        CategoryEntity retrievedCategory = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found.")
        );


        //Verifica se a Categoria passada está sendo utilizada por alguma checklist
        List<ChecklistItemEntity> checklistItems = this.checklistItemRepository.findByCategoryGuid(guid);
        if(!CollectionUtils.isEmpty(checklistItems)) {
            throw new CategoryServiceException("It is not possible to delete given category as it has been used by checklist items");
        }

        log.debug("Deleting category [ guid = {} ]", guid);

        this.categoryRepository.delete(retrievedCategory);
    }

    public Iterable<CategoryEntity> findAllCategories() {

        return this.categoryRepository.findAll();
    }

    public CategoryEntity findCategoryByGuid(String guid) {

        if(!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Category guid be empty or null");
        }

        return this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Category not found.")
        );
    }
}
