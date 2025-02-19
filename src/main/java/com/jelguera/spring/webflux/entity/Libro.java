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
@Table(name = "LIBRO",schema = "BIBLIOTECA")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @Column("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column("TITULO")
    private String titulo;

    @Column("AUTOR")
    private Integer idAutor;

    @Column("ISBN")
    private String isbn;

    @Temporal(TemporalType.DATE)
    @Column("FECHA_PUBLICACION")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaPublicacion;
    
    @Column("ESTADO")
    private Integer estado;
}
