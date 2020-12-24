package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.PackageType;
import com.lilly182.frigoapi.services.PackageTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/types")
public class PackageTypeController {
    private final PackageTypeService packageTypeService;

    public PackageTypeController(PackageTypeService packageTypeService) {
        this.packageTypeService = packageTypeService;
    }


    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Set<PackageType>> getPackageTypes() {

        Set<PackageType> packageTypes = packageTypeService.findAll();
        if (packageTypes.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any Types of packages"));
        }
        return new ResponseEntity<>(packageTypes, HttpStatus.OK);
    }

    @GetMapping("/{packageTypeId}")
    public ResponseEntity<PackageType> getPackageType(@PathVariable Long packageTypeId) {
        PackageType apackageType = packageTypeService.findById(packageTypeId);
        if (apackageType == null) throw new ResourceNotFoundException("packageType not found on : " + packageTypeId);
        return ResponseEntity.ok().body(apackageType);
    }

    @PostMapping
    public PackageType createPackageType(@Valid @RequestBody PackageType apackageType) {
        return packageTypeService.save(apackageType);
    }

    @PutMapping("/{packageTypeId}")
    public ResponseEntity<PackageType> updatePackageType(
            @PathVariable Long packageTypeId, @Valid @RequestBody PackageType packageTypeDetails)
            throws ResourceNotFoundException {

        PackageType packageType = packageTypeService.update(packageTypeId,packageTypeDetails);
        if(packageType == null) throw new ResourceNotFoundException("packageType not found on : " + packageTypeId);

        return ResponseEntity.ok(packageType);
    }

    @DeleteMapping("/{packageTypeId}")
    public void updatePackageType(Long packageTypeId){
        packageTypeService.deleteById(packageTypeId);
    }
}
