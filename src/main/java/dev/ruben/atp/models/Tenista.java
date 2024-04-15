package dev.ruben.atp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import lombok.Value;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;

@Entity
@Table(name = "tenistas")
@Data @AllArgsConstructor @NoArgsConstructor
public class Tenista {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private Long ranking;
    @Column
    private String nombreCompleto;
    private String pais;
    @Column
    private String fechaNac;
    @Column
    private Long edad;
    @Column
    private Double altura;
    @Column
    private Double peso;
    @Column
    private String fecha;
    @Column
    @Enumerated(EnumType.STRING)
    private Mano mano;
    @Column
    @Enumerated(EnumType.STRING)
    private Reves tipo;
    @Column
    private String entrenado;
    @Column
    private Long dineroGanado;
    @Column
    private Long bestRanking;
    @Column
    private Double wins;
    @Column
    private Double loses;
    @Column
    private Double winrate;
    @Column
    private String imagen;
    @CreationTimestamp
    LocalDateTime fechaCreacion;
    @UpdateTimestamp
    LocalDateTime fechaActualizacion;






}
