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

import com.jelguera.spring.webflux.entity.Prestamo;
import com.jelguera.spring.webflux.model.HttpResult;
import com.jelguera.spring.webflux.model.PrestamoRequest;
import com.jelguera.spring.webflux.service.PrestamoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/prestamo")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private static final Logger log = LoggerFactory.getLogger(PrestamoController.class);

    @Autowired
    PrestamoService prestamoService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Mono<HttpResult> create(@RequestBody PrestamoRequest prestamoRequest) {
        return prestamoService.create(prestamoRequest).map(
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

     @RequestMapping(value = "/updatePrestamo/{id}", method = RequestMethod.PUT)
    public Mono<HttpResult> update(@PathVariable Integer id, @RequestBody PrestamoRequest autorRequest) {
        return prestamoService.update(id, autorRequest).map(
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

    @RequestMapping(value = "/findAllPrestamo", method = RequestMethod.GET)
    public Mono<HttpResult> findAll() {
        return prestamoService.findAll().collectList()
                .flatMap(e -> Mono.just(new HttpResult(200, "ok", e))

                ).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> delete(@PathVariable Integer id) {
        return prestamoService.delete(id).map(
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

    @RequestMapping(value = "/buscarPrestamo", method = RequestMethod.POST)
    public Flux<Prestamo> obtenerAutores(@RequestBody PrestamoRequest filtro) {
        return prestamoService.filtrarDatos(filtro.getIdLibro(), filtro.getFechaPrestamo(), filtro.getFechaDevolucion());
        
    }


    @RequestMapping(value = "/obtenerDataPrestamo/{id}", method = RequestMethod.GET)
    public Mono<HttpResult> obtenerDataPrestamo(@PathVariable Integer id) {
        return prestamoService.obtenerDataPrestamo(id).map(
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
