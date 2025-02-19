package com.jelguera.spring.webflux.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelguera.spring.webflux.entity.Libro;
import com.jelguera.spring.webflux.model.EstadoRequest;
import com.jelguera.spring.webflux.model.LibroRequest;
import com.jelguera.spring.webflux.repository.LibroRepository;
import com.jelguera.spring.webflux.repository.LibroRepository2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LibroService {
    

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroRepository2 libroRepository2;
 
    
    
    public Mono<Libro> createBook(LibroRequest libroRequest) {
        Libro c = Libro.builder()
        .titulo(libroRequest.getTitulo())
        .idAutor(libroRequest.getIdAutor())
        .isbn(libroRequest.getIsbn())
        .fechaPublicacion(libroRequest.getFechaPublicacion())
        .estado(1)
        .build(); 
        return  libroRepository.save(c)
        .doOnError(error -> {
            // Log error here
            System.err.println("Error saving create book: " + error.getMessage());
        });
            
    }

    public Mono<Libro> updateBook(Integer id, LibroRequest libroRequest) {
        return libroRepository.findById(id)
            .flatMap(book -> {
                Libro updatedBook = Libro.builder()
                    .id(book.getId())
                    .titulo(libroRequest.getTitulo())
                    .idAutor(libroRequest.getIdAutor())
                    .isbn(libroRequest.getIsbn())
                    .fechaPublicacion(libroRequest.getFechaPublicacion())
                    .estado(1)
                    .build();
    
                return libroRepository.save(updatedBook);
            })
            .doOnError(error -> {
                // Log error here
                System.err.println("Error saving updated book: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el libro con ID: " + id)));
    }

    public Flux<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Mono<Libro> delete(Integer id) {
        return libroRepository.findById(id)
            .flatMap(book -> {
                if (book == null) {
                    return Mono.error(new RuntimeException("No se encontró el libro con ID: " + id));
                }
    
                Libro updatedBook = Libro.builder()
                    .id(book.getId())
                    .titulo(book.getTitulo())
                    .idAutor(book.getIdAutor())
                    .isbn(book.getIsbn())
                    .fechaPublicacion(book.getFechaPublicacion())
                    .estado(0)
                    .build();
    
                // Guarda el libro actualizado
                return libroRepository.save(updatedBook);
            })
            .doOnError(error -> {
                // Log error here
                System.err.println("Error saving updated book: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el libro con ID: " + id)));
    }

    public Mono<Libro> activar(Integer id) {
        return libroRepository.findById(id)
            .flatMap(book -> {
                if (book == null) {
                    return Mono.error(new RuntimeException("No se encontró el libro con ID: " + id));
                }
    
                Libro updatedBook = Libro.builder()
                    .id(book.getId())
                    .titulo(book.getTitulo())
                    .idAutor(book.getIdAutor())
                    .isbn(book.getIsbn())
                    .fechaPublicacion(book.getFechaPublicacion())
                    .estado(1)
                    .build();
    
                // Guarda el libro actualizado
                return libroRepository.save(updatedBook);
            })
            .doOnError(error -> {
                // Log error here
                System.err.println("Error saving updated book: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el libro con ID: " + id)));
    }

     public Flux<Libro> filtrarDatos(String titulo, LocalDate fechaPublicacion, Integer idAutor, Integer estado ) {
        return libroRepository2.findAll(titulo, fechaPublicacion, idAutor, estado);
    }

    public Mono<Libro> obtenerDataLibro(Integer id) {
        return libroRepository.findById(id);
    }

    public Flux<EstadoRequest> obtenerEstado() {
        return Flux.just(
                "codigo: 1, nombreEstado: Disponible",
                "codigo: 0, nombreEstado: No Disponible"
            )
            .map(item -> {
                String[] parts = item.split(", ");
                String codigo = null;
                String nombreEstado = null;

                for (String part : parts) {
                    String[] keyValue = part.split(": ");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        if ("codigo".equals(key)) {
                            codigo = value;
                        } else if ("nombreEstado".equals(key)) {
                            nombreEstado = value;
                        }
                    }
                }

                return new EstadoRequest(codigo, nombreEstado);
            });
    }
}
    
