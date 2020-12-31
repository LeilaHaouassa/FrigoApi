package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Package;
import com.lilly182.frigoapi.models.Storehouse;
import com.lilly182.frigoapi.services.PackageService;
import com.lilly182.frigoapi.services.StorehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}/storehouses/{storehouseId}/packages")
public class PackageController {
    private final PackageService packageService;
    private final StorehouseService storehouseService;

    public PackageController(PackageService packageService, StorehouseService storehouseService) {
        this.packageService = packageService;
        this.storehouseService = storehouseService;
    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("storehouse")
    public Storehouse findStorehouse(@PathVariable("storehouseId") Long storehouseId) {
        return storehouseService.findById(storehouseId);
    }

    @GetMapping({"", "/"})
    public String getPackages(Model model) {

        Set<Package> packages = packageService.findAll();
        if (packages.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any packages"));
        }
        model.addAttribute("packages",packages);
        return "packages/list";
    }

    @GetMapping("/{packageId}")
    public String getPackage(@PathVariable Long packageId, Model model) {
        Package aPackage = packageService.findById(packageId);
        if (aPackage == null) throw new ResourceNotFoundException("package not found on : " + packageId);
        model.addAttribute("package",aPackage);
        return "packages/details";
    }

    @GetMapping("/new")
    public String createPackage(Storehouse storehouse, Model model){
        Package newPackage = packageService.addPackageToStorehouse(storehouse,new Package());
        model.addAttribute("package",newPackage);
        return "packages/createOrUpdate";
    }

    @PostMapping("/new")
    public String createPackage(Storehouse storehouse, @Valid  Package aPackage, BindingResult result, Model model) {
        if(result.hasErrors()){
            aPackage.setStorehouse(storehouse);
            model.addAttribute("package",aPackage);
            return "packages/createOrUpdate";
        }else{
            Package createdPackage = packageService.addPackageToStorehouse(storehouse,aPackage);
            createdPackage = packageService.save(createdPackage);
            return "redirect:/owners/{ownerId}/storehouses/{storehouseId}/packages"+ createdPackage.getId().toString();
        }
    }

    @GetMapping("/{packageId}/edit")
    public String updatePackage( @PathVariable Long packageId, Model model ){
        model.addAttribute("package",packageService.findById(packageId));
        return "packages/createOrUpdate";
    }


    @PostMapping("/{packageId}/edit")
    public String updatePackage(
            @PathVariable Long packageId, @Valid Package packageDetails, BindingResult result, Model model, Storehouse storehouse)
            throws ResourceNotFoundException {
        if(result.hasErrors()){
            packageDetails.setStorehouse(storehouse);
            model.addAttribute("package",packageDetails);
            return "packages/createOrUpdate";
        }else{
            Package savedPackage = packageService.addPackageToStorehouse(storehouse,packageDetails);
            savedPackage = packageService.update(packageId,savedPackage);
            if(savedPackage == null) throw new ResourceNotFoundException("package not found on : " + packageId);
            return "redirect:/owners/{ownerId}/storehouses/{storehouseId}/packages" + savedPackage.getId().toString();
        }
    }

    @DeleteMapping("/{packageId}")
    public void updatePackage(Long packageId){
        packageService.deleteById(packageId);
    }
    
}
