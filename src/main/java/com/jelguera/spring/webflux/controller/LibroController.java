package com.jelguera.spring.webflux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jelguera.spring.webflux.entity.Libro;
import com.jelguera.spring.webflux.model.HttpResult;
import com.jelguera.spring.webflux.model.LibroRequest;
import com.jelguera.spring.webflux.service.LibroService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/libro")
@CrossOrigin(origins = "*")
public class LibroController {

    private static final Logger log = LoggerFactory.getLogger(LibroController.class);

    @Autowired
    LibroService libroService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Mono<HttpResult> create(@RequestBody LibroRequest libroRequest) {
        return libroService.createBook(libroRequest).map(
                e -> {
                    HttpResult result = new HttpResult();
                    result.setCode(1);
                    result.setData(e);
                    result.setMsg("ok");
                    return result;
                }).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/updateBook/{id}", method = RequestMethod.PUT)
    public Mono<HttpResult> update(@PathVariable Integer id, @RequestBody LibroRequest libroRequest) {
        return libroService.updateBook(id, libroRequest).map(
                e -> {
                    HttpResult result = new HttpResult();
                    result.setCode(1);
                    result.setData(e);
                    result.setMsg("ok");
                    return result;
                }).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Mono<HttpResult> findAll() {
        return libroService.findAll().collectList()
                .flatMap(e -> Mono.just(new HttpResult(200, "ok", e))

                ).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> delete(@PathVariable Integer id) {
        return libroService.delete(id).map(
                e -> {
                    HttpResult result = new HttpResult();
                    result.setCode(1);
                    result.setData(e);
                    result.setMsg("ok");
                    return result;
                }).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/activar/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> activar(@PathVariable Integer id) {
        return libroService.activar(id).map(
                e -> {
                    HttpResult result = new HttpResult();
                    result.setCode(1);
                    result.setData(e);
                    result.setMsg("ok");
                    return result;
                }).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/buscarLibro", method = RequestMethod.POST)
    public Flux<Libro> obtenerAutores(@RequestBody LibroRequest filtro) {
        return libroService.filtrarDatos(filtro.getTitulo(), filtro.getFechaPublicacion(), filtro.getIdAutor(), filtro.getEstado());
        
    }

    @RequestMapping(value = "/obtenerDataLibro/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> obtenerDataAutor(@PathVariable Integer id) {
        return libroService.obtenerDataLibro(id).map(
                e -> {
                    HttpResult result = new HttpResult();
                    result.setCode(1);
                    result.setData(e);
                    result.setMsg("ok");
                    return result;
                }).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/obtenerEstado", method = RequestMethod.GET)
    public Mono<HttpResult> obtenerEstado() {
        return libroService.obtenerEstado().collectList()
                .flatMap(e -> Mono.just(new HttpResult(200, "ok", e))

                ).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }
}
