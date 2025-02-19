package com.jelguera.spring.webflux.repository;

import java.time.LocalDate;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.jelguera.spring.webflux.entity.Autor;

import reactor.core.publisher.Flux;

@Repository
public class AutorRepository2 {

    private final DatabaseClient databaseClient;

    public AutorRepository2(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Flux<Autor> findAll(String nombre, String nacionalidad, LocalDate fechaNacimiento, String estado) {
        StringBuilder query = new StringBuilder("SELECT * FROM AUTOR WHERE 1=1");
        
        if (nombre != null && !nombre.isEmpty()) {
            query.append(" AND UPPER(NOMBRE) LIKE '%' || UPPER (:nombre) || '%'");
        }
        if (fechaNacimiento != null) {
            query.append(" AND FECHA_NACIMIENTO = :fechaNacimiento");
        }
        if (nacionalidad != null && !nacionalidad.isEmpty()) {
            query.append(" AND NACIONALIDAD = :nacionalidad");
        }
        if (estado != null && !estado.isEmpty()) {
            query.append(" AND ESTADO = :estado");
        }
    
        // Crear una especificación de consulta
        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query.toString());
    
        // Vincular parámetros si se proporcionan
        if (nombre != null && !nombre.isEmpty()) {
            spec = spec.bind("nombre", nombre);
        }
        if (fechaNacimiento != null) {
            spec = spec.bind("fechaNacimiento", fechaNacimiento);
        }
        if (nacionalidad != null && !nacionalidad.isEmpty()) {
            spec = spec.bind("nacionalidad", nacionalidad);
        }
        if (estado != null && !estado.isEmpty()) {
            spec = spec.bind("estado", estado);
        }
    
        // Ejecutar la consulta y mapear los resultados
        return spec.map(row -> new Autor(
                row.get("ID", Integer.class),
                row.get("NOMBRE", String.class),
                row.get("NACIONALIDAD", String.class),
                row.get("FECHA_NACIMIENTO", LocalDate.class),
                row.get("ESTADO", String.class)
        )).all();
    }
    
}
