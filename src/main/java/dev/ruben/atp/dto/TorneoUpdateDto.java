package dev.ruben.atp.dto;

import dev.ruben.atp.models.Modo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor

public class TorneoUpdateDto {
    private final Long idSec;
    private final String nombre;
    private final String superficie;
    private final String categoria;
    private final String ubicacion;
    private final String fechaInicio;
    private final String fechaFin;
    private final Long premio;
    private final Long entradas;
    private final String puntos;
    private final String modo;

}

