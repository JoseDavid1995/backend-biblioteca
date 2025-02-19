package com.jelguera.spring.webflux.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "PRESTAMO",schema = "BIBLIOTECA")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @Column("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column("LIBRO")
    private Integer idLibro;

    @Temporal(TemporalType.DATE)
    @Column("FECHA_PRESTAMO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaPrestamo;

    @Temporal(TemporalType.DATE)
    @Column("FECHA_DEVOLUCION")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaDevolucion;

    @Column("ESTADO")
    private Integer estado;
    
}
