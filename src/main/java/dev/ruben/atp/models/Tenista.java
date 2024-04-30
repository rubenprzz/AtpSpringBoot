package dev.ruben.atp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static jakarta.persistence.EnumType.*;

@Entity
@Builder
@Table(name = "tenistas")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tenista {
    public static final String IMAGE_DEFAULT = "https://wallpapercave.com/wp/wp9043842.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long ranking;

    @Column(name = "nombreCompleto")
    private String nombreCompleto;

    @Column(name = "pais")
    private String pais;
    @Column
    private LocalDate fechaNacimiento;
    @Column(name = "edad")
    private Long edad;
    @Column
    private Double altura;
    @Column
    private Double peso;
    @Column
    private LocalDate fechaProfesional;
    @Column
    @Enumerated(EnumType.STRING)
    private Mano mano;
    @Column(name = "reves")
    @Enumerated(EnumType.STRING)
    private Reves reves;
    @Column
    private String entrenador;
    @Column
    private Double dineroGanado;
    @Column

    private Long bestRanking;
    @Enumerated(EnumType.STRING)
    @Column
    private Modo modo;
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
    @Getter
    @Column(name = "puntos")
    private Long puntos;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;

    public Long getEdad() {
        if(fechaNacimiento != null) {
            return edad= (long) Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }
        else
            return null;
    }
    @ManyToOne
    @JoinColumn(name = "torneo_id")
    @JsonBackReference
    private Torneo torneo;

    public Tenista(Long id) {
        this.id = id;
    }






}
