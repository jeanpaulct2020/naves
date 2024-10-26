package com.jeancot.naves.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) //ignora los null
public class NaveDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String nombre;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String tipoNave;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 512)
    private String descripcion;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 512)
    private String caracteristica;
    @NotNull
    @NotEmpty
    private String medioVisual;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 512)
    private String nombreFilm;
}
