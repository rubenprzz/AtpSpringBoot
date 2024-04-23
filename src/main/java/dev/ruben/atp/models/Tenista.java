package dev.ruben.atp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;

@Entity
@Builder
@Table(name = "tenistas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tenista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long ranking;

    @Column(name = "nombreCompleto")
    private String nombreCompleto;

    @Column(name = "pais")
    private String pais;
    @Column(name = "fecha_nacimiento")
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
    @Column(name = "reves")
    @Enumerated(EnumType.STRING)
    private Reves reves;
    @Column
    private String entrenado;
    @Column(name = "dinero_ganado")
    private Double dineroGanado;
    @Column(name = "best_ranking")
    private Long bestRanking;
    @Column
    private Double wins;
    @Column
    private Double loses;

    public Double getWinrate() {
        if (loses != 0) {
            winrate = (wins / (wins + loses)) * 100;

        } else {
            winrate = 100.0;
        }
        return (double) Math.round(winrate);
    }



    @Column(name = "winrate")
    @DecimalMax(value = "100.00")
    private Double winrate;
    @Column
    private String imagen;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;




}
