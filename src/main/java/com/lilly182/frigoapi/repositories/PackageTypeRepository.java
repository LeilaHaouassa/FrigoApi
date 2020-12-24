package com.lilly182.frigoapi.repositories;

import com.lilly182.frigoapi.models.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PackageTypeRepository extends JpaRepository<PackageType,Long> {
    Set<PackageType> findAllByNameContainingIgnoreCase(String name);
}
