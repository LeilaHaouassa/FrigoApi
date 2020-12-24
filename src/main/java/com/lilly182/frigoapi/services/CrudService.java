package com.lilly182.frigoapi.services;

import java.util.Set;

public interface CrudService<T,ID> {
    Set<T> findAll();

    T findById(ID id);

    T update(ID id,T object);

    T save(T object);


    void delete(T object);

    void deleteById(ID id);
}
