package com.lilly182.frigoapi.repositories;

import com.lilly182.frigoapi.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
    Set<Owner> findAllByLastNameContainingIgnoreCase(String lastName);
    Set<Owner> findAllByFirstNameContainingIgnoreCase(String firstName);

}
