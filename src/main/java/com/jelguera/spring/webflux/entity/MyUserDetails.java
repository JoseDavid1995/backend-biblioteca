package com.jelguera.spring.webflux.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;


 
@Table(name = "USUARIO",schema = "BIBLIOTECA")
@Setter
@Getter
public class MyUserDetails implements UserDetails {
    
    @Id
    @Column("ID_USUARIO")
    private Integer idUsuario;
    @Column("USUARIO")
    private String username;
    @Column("CONTRASENIA")
    private String password;
    @Column("FECHA_CREACION")
    private Timestamp fechaCreacion;

    
    @Column("AUTHORITIES")
    private String authorities;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

     
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

 
     
    
}
