package com.rentalcar.crud.rentalcarapi.controller;

import com.rentalcar.crud.rentalcarapi.dto.ChecklistItemDTO;
import com.rentalcar.crud.rentalcarapi.entity.ChecklistItemEntity;
import com.rentalcar.crud.rentalcarapi.service.ChecklistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/v1/api/checklist-items")
public class ChecklistItemController {

    ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }

    @GetMapping(value="/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChecklistItemDTO>> getAllChecklistItems() {

        List<ChecklistItemDTO> resp = StreamSupport.stream(this.checklistItemService.findAllChecklistItems().spliterator(), false)
                        .map(ChecklistItemEntity -> ChecklistItemDTO.toDTO(ChecklistItemEntity))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNewChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO) {

        ChecklistItemEntity newChecklistItem = this.checklistItemService.addNewChecklistItem(
                checklistItemDTO.getDescription(), checklistItemDTO.getIsCompleted(),
                checklistItemDTO.getDeadline(), checklistItemDTO.getCategory());

        return new ResponseEntity<>(newChecklistItem.getGuid(), HttpStatus.CREATED);
    }

    @PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO) {

        if(!StringUtils.hasText(checklistItemDTO.getGuid())) {
            throw new ValidationException("Checklist item cannot be null or empty");
        }

        this.checklistItemService.updateChecklistItem(checklistItemDTO.getGuid(),
                checklistItemDTO.getDescription(),
                checklistItemDTO.getIsCompleted(),
                checklistItemDTO.getDeadline(),
                checklistItemDTO.getCategory());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value="{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteChecklistItem(@PathVariable String guid) {
        this.checklistItemService.deleteChecklistItem(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
