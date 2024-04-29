package dev.ruben.atp.dto;

import dev.ruben.atp.models.Categoria;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TorneoCreateDto {

        private Long idSec;
        @NotBlank
        @Length(min = 3, max = 50)
        private String ubicacion;
        @NotBlank
        @Length(min = 3, max = 50)
        private String modo;
        @NotBlank
        @Length(min = 3, max = 50)
        @Enumerated
        private Categoria categoria;
        @CreatedDate
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;
        @NotBlank
        @Length(min = 3, max = 50)
        private String superficie;
        @NotNull
        @Positive
        private Long premio;
        @NotNull
        @PositiveOrZero
        private Long entradas;

}
