package com.jelguera.spring.webflux.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.jelguera.spring.webflux.entity.Libro;

@Repository
public interface LibroRepository extends R2dbcRepository<Libro, Integer> {

    
}
