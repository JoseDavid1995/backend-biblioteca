package com.jelguera.spring.webflux.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.jelguera.spring.webflux.entity.Nacionalidad;

@Repository
public interface NacionalidadRepository extends R2dbcRepository<Nacionalidad, Integer> {
     
}
