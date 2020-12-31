package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.PackageType;
import com.lilly182.frigoapi.services.PackageTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/types")
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
    public String  getPackageTypes(Model model) {

        Set<PackageType> packageTypes = packageTypeService.findAll();
        if (packageTypes.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any Types of packages"));
        }
        model.addAttribute("packageTypes",packageTypes);
        return "packageTypes/list";
    }
// Not needed for the moment but i'm keeping it just in case.
    @GetMapping("/{packageTypeId}")
    public String getPackageType(@PathVariable Long packageTypeId,Model model) {
        PackageType apackageType = packageTypeService.findById(packageTypeId);
        if (apackageType == null) throw new ResourceNotFoundException("packageType not found on : " + packageTypeId);
        model.addAttribute("packageType",apackageType);
        return "packageTypes/details";
    }

    @GetMapping("/new")
    public String createPackageType(Model model){
        model.addAttribute("packageType",new PackageType());
        return "packageTypes/createOrUpdate";
    }


    @PostMapping("/new")
    public String createPackageType(@Valid  PackageType apackageType, BindingResult result) {
        if(result.hasErrors()){
            return "packageTypes/createOrUpdate";
        }else {
            PackageType savedPackageType = packageTypeService.save(apackageType);
            return "redirect:/types/" ;
        }
    }


    @GetMapping("/{packageTypeId}/edit")
    public String updatePackageType(@PathVariable Long packageTypeId, Model model){
        model.addAttribute("packageType",packageTypeService.findById(packageTypeId));
        return "packageTypes/createOrUpdate";
    }
    @PostMapping("/{packageTypeId}/edit")
    public String updatePackageType(
            @PathVariable Long packageTypeId, @Valid  PackageType packageTypeDetails, BindingResult result)
            throws ResourceNotFoundException {
        if (result.hasErrors()) {
            return "packageTypes/createOrUpdate";
        }else{
            PackageType updatedPackageType = packageTypeService.update(packageTypeId,packageTypeDetails);
            if(updatedPackageType == null) throw new ResourceNotFoundException("packageType not found on : " + packageTypeId);
            return "redirect:/types/";
        }
    }

    @DeleteMapping("/{packageTypeId}")
    public void updatePackageType(Long packageTypeId){
        packageTypeService.deleteById(packageTypeId);
    }
}
