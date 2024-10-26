package com.jeancot.naves.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Nave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String tipoNave;
    private String descripcion;
    private String caracteristica;
    @Enumerated(EnumType.STRING) // Guarda el nombre del medio en la BD H2
    private MedioVisual medioVisual;
    private String nombreFilm;
}
