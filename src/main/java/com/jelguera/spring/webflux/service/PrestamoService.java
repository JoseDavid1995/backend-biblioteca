package com.jelguera.spring.webflux.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelguera.spring.webflux.entity.Prestamo;
import com.jelguera.spring.webflux.model.PrestamoRequest;
import com.jelguera.spring.webflux.repository.PrestamoRepository;
import com.jelguera.spring.webflux.repository.PrestamoRepository2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PrestamoService {
    

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoRepository2 prestamoRepository2;
 
    
    
    public Mono<Prestamo> create(PrestamoRequest prestamoRequest) {
        Prestamo c = Prestamo.builder()
        .idLibro(prestamoRequest.getIdLibro())
        .fechaPrestamo(prestamoRequest.getFechaPrestamo())
        .fechaDevolucion(prestamoRequest.getFechaDevolucion())
        .estado(1)
        .build(); 
        return  prestamoRepository.save(c)
        .doOnError(error -> {
            // Log error here
            System.err.println("Error saving create book: " + error.getMessage());
        });
            

    }

     public Mono<Prestamo> update(Integer id, PrestamoRequest prestamoRequest) {
        return prestamoRepository.findById(id)
            .flatMap(prestamo -> {
                Prestamo updatedPrestamo = Prestamo.builder()
                    .id(id)
                    .idLibro(prestamoRequest.getIdLibro())
                    .fechaPrestamo(prestamoRequest.getFechaPrestamo())
                    .fechaDevolucion(prestamoRequest.getFechaDevolucion())
                    .estado(1)
                    .build();
    
                return prestamoRepository.save(updatedPrestamo);
            })
            .doOnError(error -> {
                // Log error here
                System.err.println("Error saving updated book: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el libro con ID: " + id)));
    }

   
    public Flux<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    public Mono<Prestamo> delete(Integer id) {
        return prestamoRepository.findById(id)
            .flatMap(prestamo -> {
                Prestamo delete = Prestamo.builder()
                    .id(prestamo.getId())
                    .idLibro(prestamo.getIdLibro())
                    .fechaPrestamo(prestamo.getFechaPrestamo())
                    .fechaDevolucion(prestamo.getFechaDevolucion())
                    .estado(0)
                    .build();
    
                // Guarda el libro actualizado
                return prestamoRepository.save(delete);
            })
            .doOnError(error -> {
                // Log error here
                System.err.println("Error saving updated book: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el libro con ID: " + id)));
    }

      public Flux<Prestamo> filtrarDatos(Integer idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion ) {
        return prestamoRepository2.findAll(idLibro, fechaPrestamo, fechaDevolucion);
    }

    public Mono<Prestamo> obtenerDataPrestamo(Integer id) {
        return prestamoRepository.findById(id);
    }
}
    
