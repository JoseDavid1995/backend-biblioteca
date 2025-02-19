package com.jelguera.spring.webflux.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AUTOR", schema = "BIBLIOTECA")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @Column("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column("NOMBRE")
    private String nombre;

    @Column("NACIONALIDAD")
    private String nacionalidad;

    @Temporal(TemporalType.DATE)
    @Column("FECHA_NACIMIENTO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

    @Column("ESTADO")
    private String estado;
    
}
