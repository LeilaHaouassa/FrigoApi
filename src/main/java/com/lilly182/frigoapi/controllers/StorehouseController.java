package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.models.Storehouse;
import com.lilly182.frigoapi.services.OwnerService;
import com.lilly182.frigoapi.services.StorehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}/storehouses")
public class StorehouseController {
    private final StorehouseService storehouseService;
    private final OwnerService ownerService;

    public StorehouseController(StorehouseService storehouseService, OwnerService ownerService) {
        this.storehouseService = storehouseService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }


    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/"})
    public String getStorehouses(Model model) {

        Set<Storehouse> storehouses = storehouseService.findAll();
        if (storehouses.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any storehouses"));
        }
        model.addAttribute("storehouses",storehouses);
        return "storehouses/list";
    }
    //so far not needed but just in case
    @GetMapping("/{storehouseId}")
    public String getStorehouse(@PathVariable Long storehouseId) {
        Storehouse storehouse = storehouseService.findById(storehouseId);
        if (storehouse == null) throw new ResourceNotFoundException("storehouse not found on : " + storehouseId);
        return "/owners/"+ storehouse.getOwner().getId().toString();
    }

    @GetMapping("/new")
    public  String createStorehouse(Owner owner,Model model){
        Storehouse storehouse= storehouseService.addStorehouseToOwner(owner,new Storehouse());
        model.addAttribute("storehouse",storehouse);
        return "storehouses/createOrUpdate";
    }
    @PostMapping("/new")
    public String createStorehouse(Owner owner, @Valid  Storehouse storehouse , BindingResult result , Model model) {
        if (StringUtils.hasLength(storehouse.getLocation()) && storehouse.isNew() && owner.getStorehouseByLocation(storehouse.getLocation(), true) != null) {
            result.rejectValue("location", "duplicate", "already exists");
        }
        if(result.hasErrors()){
            storehouse.setOwner(owner);
            model.addAttribute("storehouse",storehouse);
            return "storehouses/createOrUpdate";
        }else{

            Storehouse createdStorehouse = storehouseService.addStorehouseToOwner(owner,storehouse);
            createdStorehouse =  storehouseService.save(createdStorehouse);
            return "redirect:/owners/" + owner.getId().toString();
        }
    }

    @GetMapping("/{storehouseId}/edit")
    public String updateStorehouse(@PathVariable Long storehouseId,Model model){
        model.addAttribute("storehouse",storehouseService.findById(storehouseId));
        return "storehouses/createOrUpdate";
    }

    @PostMapping("/{storehouseId}/edit")
    public String updateStorehouse(
            @PathVariable Long storehouseId, @Valid Storehouse storehouseDetails, BindingResult result, Owner owner, Model model)
            throws ResourceNotFoundException {
        if(result.hasErrors()){
            storehouseDetails.setOwner(owner);
            return "storehouses/createOrUpdate";
        }else{
            Storehouse updatedStorehouse = storehouseService.addStorehouseToOwner(owner,storehouseDetails);
            updatedStorehouse = storehouseService.update(storehouseId,updatedStorehouse);
            if(updatedStorehouse == null) throw new ResourceNotFoundException("storehouse not found on : " + storehouseId);
            return "redirect:/owners/" + owner.getId().toString();
        }
    }

    @DeleteMapping("/{storehouseId}/delete")
    public void updateStorehouse(Long storehouseId){
        storehouseService.deleteById(storehouseId);
    }
}
