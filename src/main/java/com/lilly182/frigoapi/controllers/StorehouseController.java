package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Storehouse;
import com.lilly182.frigoapi.services.StorehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/owners/{ownerId}/storehouses")
public class StorehouseController {
    private final StorehouseService storehouseService;

    public StorehouseController(StorehouseService storehouseService) {
        this.storehouseService = storehouseService;
    }


    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Set<Storehouse>> getStorehouses() {

        Set<Storehouse> storehouses = storehouseService.findAll();
        if (storehouses.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any storehouses"));
        }
        return new ResponseEntity<>(storehouses, HttpStatus.OK);
    }

    @GetMapping("/{storehouseId}")
    public ResponseEntity<Storehouse> getStorehouse(@PathVariable Long storehouseId) {
        Storehouse storehouse = storehouseService.findById(storehouseId);
        if (storehouse == null) throw new ResourceNotFoundException("storehouse not found on : " + storehouseId);
        return ResponseEntity.ok().body(storehouse);
    }

    @PostMapping
    public Storehouse createStorehouse(@Valid @RequestBody Storehouse storehouse) {
        return storehouseService.save(storehouse);
    }

    @PutMapping("/{storehouseId}")
    public ResponseEntity<Storehouse> updateStorehouse(
            @PathVariable Long storehouseId, @Valid @RequestBody Storehouse storehouseDetails)
            throws ResourceNotFoundException {

        Storehouse storehouse = storehouseService.update(storehouseId,storehouseDetails);
        if(storehouse == null) throw new ResourceNotFoundException("storehouse not found on : " + storehouseId);

        return ResponseEntity.ok(storehouse);
    }

    @DeleteMapping("/{storehouseId}")
    public void updateStorehouse(Long storehouseId){
        storehouseService.deleteById(storehouseId);
    }
}
