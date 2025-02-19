package com.jelguera.spring.webflux.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NACIONALIDAD", schema = "BIBLIOTECA")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Nacionalidad {

    @Id
    @Column("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column("NOMBRE")
    private String nombreNacionalidad;
    
}
