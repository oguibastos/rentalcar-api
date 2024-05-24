package com.rentalcar.crud.rentalcarapi.controller;

import com.rentalcar.crud.rentalcarapi.dto.CategoryDTO;
import com.rentalcar.crud.rentalcarapi.entity.CategoryEntity;
import com.rentalcar.crud.rentalcarapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> resp = StreamSupport.stream(this.categoryService
                .findAllCategories().spliterator(), false)
                .map(categoryEntity -> CategoryDTO.toDTO(categoryEntity))
                .collect(Collectors.toList());

        return new ResponseEntity<List<CategoryDTO>>(resp, HttpStatus.OK);
    }

    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryDTO categoryDTO) {


        CategoryEntity newCategory = this.categoryService.addNewCategory(categoryDTO.getName());
        return new ResponseEntity<>(newCategory.getGuid(), HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDTO categoryDTO) {

        if(!StringUtils.hasText(categoryDTO.getGuid())) {
            throw new ValidationException("Category cannot be null or empty");
        }
        this.categoryService.updateCategory(categoryDTO.getGuid(), categoryDTO.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "{guid}")
    public ResponseEntity<Void> deleteCategory(@RequestBody String guid) {

        this.categoryService.deleteCategory(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
