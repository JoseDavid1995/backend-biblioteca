package com.jelguera.spring.webflux.repository;

import java.time.LocalDate;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.jelguera.spring.webflux.entity.Libro;

import reactor.core.publisher.Flux;

@Repository
public class LibroRepository2 {

    private final DatabaseClient databaseClient;

    public LibroRepository2(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Flux<Libro> findAll(String titulo, LocalDate fechaPublicacion, Integer idAutor, Integer estado) {
        StringBuilder query = new StringBuilder("SELECT * FROM LIBRO WHERE 1=1");
        
        if (titulo != null && !titulo.isEmpty()) {
            query.append(" AND UPPER(TITULO) LIKE '%' || UPPER (:titulo) || '%'");
        }
        if (fechaPublicacion != null) {
            query.append(" AND FECHA_PUBLICACION = :fechaPublicacion");
        }
        if (idAutor != null && idAutor > 0) {
            query.append(" AND AUTOR = :idAutor");
        }
        if (estado != null) {
            query.append(" AND ESTADO = :estado");
        }
    
        // Crear una especificación de consulta
        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query.toString());
    
        // Vincular parámetros si se proporcionan
        if (titulo != null && !titulo.isEmpty()) {
            spec = spec.bind("titulo", titulo);
        }
        if (fechaPublicacion != null) {
            spec = spec.bind("fechaPublicacion", fechaPublicacion);
        }
        if (idAutor != null && idAutor > 0) {
            spec = spec.bind("idAutor", idAutor);
        }
        if (estado != null) {
            spec = spec.bind("estado", estado);
        }
    
        // Ejecutar la consulta y mapear los resultados
        return spec.map(row -> new Libro(
                row.get("ID", Integer.class),
                row.get("TITULO", String.class),
                row.get("AUTOR", Integer.class),
                row.get("ISBN", String.class),
                row.get("FECHA_PUBLICACION", LocalDate.class),
                row.get("ESTADO", Integer.class)
        )).all();
    }
    
}
