package com.jelguera.spring.webflux.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class CambioRequest {

    private String monedaOrigen;
    private String monedaDestino;
    private BigDecimal montoOrigen;
    private BigDecimal montoDestino;


}
