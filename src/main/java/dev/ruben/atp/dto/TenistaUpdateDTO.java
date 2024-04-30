package dev.ruben.atp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TenistaUpdateDTO {

        private final String nombreCompleto;
        private final String pais;
        private final String entrenador;
        private final Double dineroGanado;
        private final Long loses;
        private final Long wins;
        private final Long puntos;
        private final Long edad;
        private final Double altura;
        private final Double peso;
        private final String mano;
        private final String reves;
        private final LocalDateTime fechaNacimiento;
        private final LocalDateTime fechaProfesional;
        private final Long ranking;
        private final Long bestRanking;
        private String imagen;



}
