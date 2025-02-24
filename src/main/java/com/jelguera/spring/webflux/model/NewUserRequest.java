package com.jelguera.spring.webflux.model;

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
public class NewUserRequest {

    private String usuario;
    private String contrasenia;

}
