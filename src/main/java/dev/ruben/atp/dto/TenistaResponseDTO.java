package dev.ruben.atp.dto;

import dev.ruben.atp.models.Mano;
import dev.ruben.atp.models.Tenista;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TenistaResponseDTO {
    private Long id;
    private Long ranking;
    private String nombreCompleto;
    private String pais;
    private String fechaNacimiento;
    private Long edad;
    private Double altura;
    private Double peso;
    private String mano;
    private String reves;
    private String entrenador;
    private Double dineroGanado;
    private Long bestRanking;
    private Double wins;
    private Double loses;
    private Double winrate;
    private String imagen;



}
