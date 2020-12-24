package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.services.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Set<Owner>> getOwners() {

        Set<Owner> owners = ownerService.findAll();
        if (owners.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any Owners"));
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<Owner> getOwner(@PathVariable Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        if (owner == null) throw new ResourceNotFoundException("Owner not found on :: " + ownerId);
        return ResponseEntity.ok().body(owner);
    }

    @PostMapping
    public Owner createOwner(@Valid @RequestBody Owner owner) {
        return ownerService.save(owner);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<Owner> updateOwner(
            @PathVariable Long ownerId, @Valid @RequestBody Owner ownerDetails)
            throws ResourceNotFoundException {

        Owner owner = ownerService.update(ownerId,ownerDetails);
        if(owner == null) throw new ResourceNotFoundException("Owner not found on :: " + ownerId);

        return ResponseEntity.ok(owner);
    }

    @DeleteMapping("/{ownerId}")
    public void updateOwner(Long ownerId){
         ownerService.deleteById(ownerId);
    }




}
