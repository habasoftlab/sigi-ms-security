package com.servispeed.ms_security.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Cat_Permisos")
@Data
public class Permiso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPermiso;

    private String nombreClave;

    private String descripcion;
}

