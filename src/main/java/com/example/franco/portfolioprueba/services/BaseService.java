package com.example.franco.portfolioprueba.services;

import javax.persistence.Entity;
import java.util.List;

public interface BaseService<E> {
    List<E> getAll() throws Exception;
    E getById(Long id) throws Exception;
    E save(E entity) throws Exception;
    E update(E entity, Long id) throws Exception;
    Boolean delete(Long id) throws Exception;
}
