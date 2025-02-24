package com.jelguera.spring.webflux.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelguera.spring.webflux.entity.Autor;
import com.jelguera.spring.webflux.entity.MyUserDetails;
import com.jelguera.spring.webflux.entity.Nacionalidad;
import com.jelguera.spring.webflux.model.AutorRequest;
import com.jelguera.spring.webflux.model.EstadoRequest;
import com.jelguera.spring.webflux.model.NewUserRequest;
import com.jelguera.spring.webflux.repository.AutorRepository;
import com.jelguera.spring.webflux.repository.AutorRepository2;
import com.jelguera.spring.webflux.repository.NacionalidadRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AutorService {
    

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Autowired
    private AutorRepository2 autorRepository2;

    @Autowired
    private com.jelguera.spring.webflux.repository.UserDetailsRepository UserDetailsRepository;

    
    public Mono<Autor> create(AutorRequest autorRequest) {
        Autor c = Autor.builder()
        .nombre(autorRequest.getNombre())
        .nacionalidad(autorRequest.getNacionalidad())
        .fechaNacimiento(autorRequest.getFechaNacimiento())
        .estado("A")
        .build(); 
        return  autorRepository.save(c)
        .doOnError(error -> {
            System.err.println("Error saving create book: " + error.getMessage());
        });
            

    }

    public Mono<Autor> update(Integer id, AutorRequest autorRequest) {
        return autorRepository.findById(id)
            .flatMap(autor -> {
                Autor updatedAutor = Autor.builder()
                    .id(autor.getId())
                    .nombre(autorRequest.getNombre())
                    .nacionalidad(autorRequest.getNacionalidad())
                    .fechaNacimiento(autorRequest.getFechaNacimiento())
                    .estado("A")
                    .build();
    
                return autorRepository.save(updatedAutor);
            })
            .doOnError(error -> {
                System.err.println("Error al guardar el autor actualizado: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el autor con ID: " + id)));
    }

   
    public Flux<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Mono<Autor> obtenerDataAutor(Integer id) {
        return autorRepository.findById(id);
    }

    public Mono<Autor> delete(Integer id) {
        return autorRepository.findById(id)
            .flatMap(autor -> {
                if (autor == null) {
                    return Mono.error(new RuntimeException("No se encontró el autor con ID: " + id));
                }
    
                Autor updatedAutor = Autor.builder()
                    .id(autor.getId())
                    .nombre(autor.getNombre()) 
                    .nacionalidad(autor.getNacionalidad()) 
                    .fechaNacimiento(autor.getFechaNacimiento())
                    .estado("I")
                    .build();
    
                // Guarda el autor actualizado
                return autorRepository.save(updatedAutor);
            })
            .doOnError(error -> {
                // Log error aquí
                System.err.println("Error al guardar el autor actualizado: " + error.getMessage());
            })
            .switchIfEmpty(Mono.error(new RuntimeException("No se encontró el autor con ID: " + id)));
    }

    public Flux<Nacionalidad> obtenerNacionalidades() {
        return nacionalidadRepository.findAll();
    }

    public Flux<EstadoRequest> obtenerEstado() {
        return Flux.just(
                "codigo: A, nombreEstado: Activo",
                "codigo: I, nombreEstado: Inactivo"
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

    public Flux<Autor> filtrarDatos(String nombre, String nacionalidad, LocalDate fechaNacimiento, String estado) {
        return autorRepository2.findAll(nombre, nacionalidad, fechaNacimiento, estado);
    }

    public Mono<MyUserDetails> createUser(NewUserRequest newUserRequest) {
        MyUserDetails c = new MyUserDetails();
        c.setUsername(newUserRequest.getUsuario());
        c.setPassword(newUserRequest.getContrasenia());
        c.setFechaCreacion(Timestamp.from(Instant.now()));
        return  UserDetailsRepository.save(c)
        .doOnError(error -> {
            System.err.println("Error saving create book: " + error.getMessage());
        });
            

    }
}
    
