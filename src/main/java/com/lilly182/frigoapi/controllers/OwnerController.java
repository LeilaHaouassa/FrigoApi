package com.lilly182.frigoapi.controllers;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners")
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
    public String getOwners(Model model) {

        Set<Owner> owners = ownerService.findAll();
        if (owners.isEmpty()) {
            throw (new ResourceNotFoundException("Could not Find any Owners"));
        }
        model.addAttribute("owners",owners);
        return "owners/list";
    }

    @GetMapping("/{ownerId}")
    public String getOwner(@PathVariable Long ownerId,Model model) {
        Owner owner = ownerService.findById(ownerId);
        if (owner == null) throw new ResourceNotFoundException("Owner not found on :: " + ownerId);
        model.addAttribute("owner",owner);
        return "owners/details";
    }

    @GetMapping("/new")
    public String createOwner(Model model){
        model.addAttribute("owner",new Owner());
        return "owners/createOrUpdate";
    }
    @PostMapping("/new")
    public String createOwner(@Valid  Owner owner, BindingResult result) {
        if(result.hasErrors()){
            return "owners/createOrUpdate";
        }else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/"+ savedOwner.getId().toString();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String updateOwner(@PathVariable Long ownerId, Model model){
        model.addAttribute("owner",ownerService.findById(ownerId));
        return "owners/createOrUpdate";
    }

    @PostMapping("/{ownerId}/edit")
    public String updateOwner(
            @PathVariable Long ownerId, @Valid  Owner ownerDetails , BindingResult result)
            throws ResourceNotFoundException {
        if (result.hasErrors()){
            return "owners/createOrUpdate";
        }else{
            Owner updatedOwner = ownerService.update(ownerId,ownerDetails);
            if(updatedOwner == null) throw new ResourceNotFoundException("Owner not found on :: " + ownerId);
            return "redirect:/owners/" + updatedOwner.getId().toString();
        }
    }


    @DeleteMapping("/{ownerId}")
    public void updateOwner(Long ownerId){
         ownerService.deleteById(ownerId);
    }




}
