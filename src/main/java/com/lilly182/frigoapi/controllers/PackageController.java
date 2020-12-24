package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Package;
import com.lilly182.frigoapi.services.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/owners/{ownerId}/storehouses/{storehouseId}/packages")
public class PackageController {
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }


    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Set<Package>> getPackages() {

        Set<Package> packages = packageService.findAll();
        if (packages.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any packages"));
        }
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<Package> getPackage(@PathVariable Long packageId) {
        Package aPackage = packageService.findById(packageId);
        if (aPackage == null) throw new ResourceNotFoundException("package not found on : " + packageId);
        return ResponseEntity.ok().body(aPackage);
    }

    @PostMapping
    public Package createPackage(@Valid @RequestBody Package aPackage) {
        return packageService.save(aPackage);
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<Package> updatePackage(
            @PathVariable Long packageId, @Valid @RequestBody Package packageDetails)
            throws ResourceNotFoundException {

        Package aPackage = packageService.update(packageId,packageDetails);
        if(aPackage == null) throw new ResourceNotFoundException("package not found on : " + packageId);

        return ResponseEntity.ok(aPackage);
    }

    @DeleteMapping("/{packageId}")
    public void updatePackage(Long packageId){
        packageService.deleteById(packageId);
    }
    
}
