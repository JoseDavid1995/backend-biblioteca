package com.jelguera.spring.webflux.repository;

import java.time.LocalDate;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.jelguera.spring.webflux.entity.Prestamo;

import reactor.core.publisher.Flux;

@Repository
public class PrestamoRepository2 {

    private final DatabaseClient databaseClient;

    public PrestamoRepository2(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Flux<Prestamo> findAll(Integer idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        StringBuilder query = new StringBuilder("SELECT * FROM PRESTAMO WHERE 1=1");
        
        if (idLibro != null && idLibro > 0) {
            query.append(" AND LIBRO = :idLibro");

        }
        if (fechaPrestamo != null) {
            query.append(" AND FECHA_PRESTAMO = :fechaPrestamo");
        }

        if (fechaDevolucion != null) {
            query.append(" AND FECHA_DEVOLUCION = :fechaDevolucion");
        }

    
        // Crear una especificación de consulta
        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query.toString());
    
        // Vincular parámetros si se proporcionan
        if (idLibro != null && idLibro > 0) {
            spec = spec.bind("idLibro", idLibro);
        }
        if (fechaPrestamo != null) {
            spec = spec.bind("fechaPrestamo", fechaPrestamo);
        }
        if (fechaDevolucion != null) {
            spec = spec.bind("fechaDevolucion", fechaDevolucion);
        }
    
        // Ejecutar la consulta y mapear los resultados
        return spec.map(row -> new Prestamo(
                row.get("ID", Integer.class),
                row.get("LIBRO", Integer.class),
                row.get("FECHA_PRESTAMO", LocalDate.class),
                row.get("FECHA_DEVOLUCION", LocalDate.class),
                row.get("ESTADO", Integer.class)
        )).all();
    }
    
}
