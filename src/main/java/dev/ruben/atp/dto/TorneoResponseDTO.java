package dev.ruben.atp.dto;

import dev.ruben.atp.models.Participante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TorneoResponseDTO {

    private Long idSec;
    private String ubicacion;
    private String modo;
    private String categoria;
    private String fechaInicio;
    private String fechaFin;
    private String superficie;
    private Long premio;
    private Long entradas;
    private Long puntos;
    private Set<Participante> participantes;




}
