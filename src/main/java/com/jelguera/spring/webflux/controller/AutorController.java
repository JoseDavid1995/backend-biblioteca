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

import com.jelguera.spring.webflux.entity.Autor;
import com.jelguera.spring.webflux.model.AutorRequest;
import com.jelguera.spring.webflux.model.HttpResult;
import com.jelguera.spring.webflux.service.AutorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/autor")
@CrossOrigin(origins = "*")
public class AutorController {

    private static final Logger log = LoggerFactory.getLogger(AutorController.class);

    @Autowired
    AutorService autorService;

    @RequestMapping(value = "/createAutor", method = RequestMethod.POST)
    public Mono<HttpResult> create(@RequestBody AutorRequest autorRequest) {
        return autorService.create(autorRequest).map(
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

     @RequestMapping(value = "/updateAutor/{id}", method = RequestMethod.PUT)
    public Mono<HttpResult> update(@PathVariable Integer id, @RequestBody AutorRequest autorRequest) {
        return autorService.update(id, autorRequest).map(
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
        return autorService.findAll().collectList()
                .flatMap(e -> Mono.just(new HttpResult(200, "ok", e))

                ).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/deleteAutor/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> delete(@PathVariable Integer id) {
        return autorService.delete(id).map(
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

    @RequestMapping(value = "/obtenerNacionalidades", method = RequestMethod.GET)
    public Mono<HttpResult> obtenerNacionalidades() {
        return autorService.obtenerNacionalidades().collectList()
                .flatMap(e -> Mono.just(new HttpResult(200, "ok", e))

                ).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }


    @RequestMapping(value = "/obtenerEstado", method = RequestMethod.GET)
    public Mono<HttpResult> obtenerEstado() {
        return autorService.obtenerEstado().collectList()
                .flatMap(e -> Mono.just(new HttpResult(200, "ok", e))

                ).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/buscarAutor", method = RequestMethod.POST)
    public Flux<Autor> obtenerAutores(@RequestBody AutorRequest filtro) {
        return autorService.filtrarDatos(filtro.getNombre(), filtro.getNacionalidad(), filtro.getFechaNacimiento(), filtro.getEstado());
        
    }


    @RequestMapping(value = "/obtenerDataAutor/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> obtenerDataAutor(@PathVariable Integer id) {
        return autorService.obtenerDataAutor(id).map(
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


}
