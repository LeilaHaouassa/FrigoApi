package com.lilly182.frigoapi.services;

import com.lilly182.frigoapi.models.PackageType;
import com.lilly182.frigoapi.repositories.PackageTypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PackageTypeService implements CrudService<PackageType,Long> {

    private final PackageTypeRepository packageTypeRepository;

    public PackageTypeService(PackageTypeRepository packageTypeRepository) {
        this.packageTypeRepository = packageTypeRepository;
    }

    @Override
    public Set<PackageType> findAll() {
        Set<PackageType> packageTypes = new HashSet<>();
        packageTypeRepository.findAll().forEach(packageTypes::add);
        return packageTypes;
    }

    @Override
    public PackageType findById(Long id) {
        return packageTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PackageType update(Long packageTypeId,PackageType packageTypeDetails) {
        PackageType updatedPackageType= findById(packageTypeId);
        if(updatedPackageType != null){
            updatedPackageType.setName(packageTypeDetails.getName());

            updatedPackageType = packageTypeRepository.save(updatedPackageType);
        }
        return updatedPackageType;
    }

    @Override
    public PackageType save(PackageType packageType) {
        return packageTypeRepository.save(packageType);
    }

    @Override
    public void delete(PackageType packageType) {
        packageTypeRepository.delete(packageType);
    }

    @Override
    public void deleteById(Long id) {
        packageTypeRepository.deleteById(id);
    }
}
