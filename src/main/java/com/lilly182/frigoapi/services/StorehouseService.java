package com.lilly182.frigoapi.services;

import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.models.Storehouse;
import com.lilly182.frigoapi.repositories.StorehouseRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class StorehouseService implements CrudService<Storehouse,Long> {

    private final StorehouseRepository storehouseRepository;

    public StorehouseService(StorehouseRepository storehouseRepository) {
        this.storehouseRepository = storehouseRepository;
    }

    @Override
    public Set<Storehouse> findAll() {
        Set<Storehouse> storehouses = new HashSet<>();
        storehouseRepository.findAll().forEach(storehouses::add);
        return storehouses;
    }

    @Override
    public Storehouse findById(Long id) {
        return storehouseRepository.findById(id).orElse(null);
    }

    @Override
    public Storehouse update(Long storehouseId,Storehouse storehouseDetails) {
        Storehouse updatedStorehouse = findById(storehouseId);
        if (updatedStorehouse != null){
            updatedStorehouse.setLocation(storehouseDetails.getLocation());
            updatedStorehouse.setName(storehouseDetails.getName());
            updatedStorehouse = storehouseRepository.save(updatedStorehouse);
        }
        return updatedStorehouse;

    }

    @Override
    public Storehouse save(Storehouse storehouse) {
        return storehouseRepository.save(storehouse);
    }

    @Override
    public void delete(Storehouse storehouse) {
        storehouseRepository.delete(storehouse);
    }

    @Override
    public void deleteById(Long storehouseId) {
        Storehouse storehouseToDelete = findById(storehouseId);
        if (storehouseToDelete == null)throw  new ResourceNotFoundException("No Storehouse was found with such id " + storehouseId);

        storehouseRepository.deleteById(storehouseId);

    }

    public Storehouse addStorehouseToOwner(Owner owner, Storehouse storehouse){
        owner.getStorehouseList().add(storehouse);
        storehouse.setOwner(owner);
        return storehouse;
    }
}
