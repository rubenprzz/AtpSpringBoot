package dev.ruben.atp.dto;

import dev.ruben.atp.models.Mano;
import dev.ruben.atp.models.Reves;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenistaCreateDTO {
    @Positive
    private long bestRanking;
    @Positive
    private Long ranking;
    @NotBlank
    @Length(min = 4, max = 50)
    private String nombreCompleto;
    @NotBlank
    @Length(min = 1, max = 50)
    private String pais;
    private LocalDate fechaNacimiento;
    @NotNull
    @Positive
    @Max(value = 300)
    private Double altura;
    @NotNull
    @Positive
    private Double peso;
    private LocalDate fechaProfesional;
    @NotNull
    @Enumerated
    private Mano mano;
    @NotNull
    @Enumerated
    private Reves reves;
    @NotBlank
    @Length(min = 3,max=50)
    private String entrenador;
    @NotNull
    @PositiveOrZero
    private Double dineroGanado;
    @NotNull
    @PositiveOrZero
    private Long loses;
    @NotNull
    @PositiveOrZero
    private Long wins;
    @NotNull
    @PositiveOrZero
    private Long puntos;
    @Positive
    private Long edad;
    @Positive
    private Double winrate;
    @NotNull
    private String imagen;


    public Long getEdad() {
        if(fechaNacimiento != null) {
            return edad= (long) Period.between(LocalDate.from(fechaNacimiento), LocalDate.now()).getYears();
        }
        else
            return null;
    }
    public Double getWinrate() {
        if (loses != null && loses != 0) {
            winrate = (double) ((wins / (wins + loses)) * 100);
        } else {
            winrate = 100.0;
        }
        return (double) Math.round(winrate);
    }




}
