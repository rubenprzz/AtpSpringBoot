package dev.ruben.atp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "torneo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    String ubicacion;
    @Enumerated(EnumType.STRING)
    @Column
    Modo modo;
    @Enumerated(EnumType.STRING)
    @Column
    Categoria categoria;
    @Column
    String fechaInicio;
    @Column
    String fechaFin;
    @Column
    String superficie;
    @Column
    Long premio;
    @Column
    Long entradas;


}
