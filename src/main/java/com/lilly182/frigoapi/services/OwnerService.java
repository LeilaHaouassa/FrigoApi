package com.lilly182.frigoapi.services;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.repositories.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
@Transactional
@Service
public class OwnerService implements CrudService<Owner,Long> {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owners::add);
        return owners;
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Owner update(Long ownerId,Owner ownerDetails) {
        Owner updatedOwner = findById(ownerId);
        if(updatedOwner != null){
            updatedOwner.setAddress(ownerDetails.getAddress());
            updatedOwner.setAge(ownerDetails.getAge());
            updatedOwner.setEmail(ownerDetails.getEmail());
            updatedOwner.setFirstName(ownerDetails.getFirstName());
            updatedOwner.setLastName(ownerDetails.getLastName());
            updatedOwner.setPhoneNumber(ownerDetails.getPhoneNumber());
            updatedOwner = ownerRepository.save(updatedOwner);
        }

        return updatedOwner;
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long ownerId) {
        Owner ownerToDelete = findById(ownerId);
        if (ownerToDelete == null)throw  new ResourceNotFoundException("No Owner was found with such id");

        ownerRepository.deleteById(ownerId);
    }

    public Set<Owner> findByLastName(String lastName){
        return ownerRepository.findAllByLastNameContainingIgnoreCase(lastName);
    }

    public Set<Owner> findByFirstName(String firstName){
        return ownerRepository.findAllByFirstNameContainingIgnoreCase(firstName);
    }


}
