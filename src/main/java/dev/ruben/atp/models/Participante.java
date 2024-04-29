package dev.ruben.atp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "participante")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenista_id")
    private Tenista tenista;

    private String resultado;

    private Long puntosOtorgados;
    private Double dineroGanado;


    @ToString.Exclude


    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    public void setPuntosBasedOnResult(String categoria) {
        if (resultado.equals("Campeon") && categoria.equals("MASTER_1000")) {
            puntosOtorgados = 1000L;
        }
        if (resultado.equals("Campeon") && categoria.equals("MASTER_500")) {
            puntosOtorgados = 500L;
        }
        if (resultado.equals("Campeon") && categoria.equals("MASTER_250")) {
            puntosOtorgados = 250L;
        }
        if (resultado.equals("Segundo") && categoria.equals("MASTER_1000")) {
            puntosOtorgados = 600L;
        }
        if (resultado.equals("Segundo") && categoria.equals("MASTER_500")) {
            puntosOtorgados = 300L;
        }
        if (resultado.equals("Segundo") && categoria.equals("MASTER_250")) {
            puntosOtorgados = 150L;
        }
        if (resultado.equals("Tercero") && categoria.equals("MASTER_1000")) {
            puntosOtorgados = 400L;
        }
        if (resultado.equals("Tercero") && categoria.equals("MASTER_500")) {
            puntosOtorgados = 200L;
        }
        if (resultado.equals("Tercero") && categoria.equals("MASTER_250"))
            puntosOtorgados = 100L;
    }

    public void setDineroGanadoBasedOnResult(String categoria) {
        if (resultado.equals("Campeon") && categoria.equals("MASTER_1000")) {
            this.dineroGanado = 1000000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Campeon") && categoria.equals("MASTER_500")) {
            this.dineroGanado = 500000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Campeon") && categoria.equals("MASTER_250")) {
            this.dineroGanado = 250000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Segundo") && categoria.equals("MASTER_1000")) {
            this.dineroGanado = 600000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Segundo") && categoria.equals("MASTER_500")) {
            this.dineroGanado = 300000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Segundo") && categoria.equals("MASTER_250")) {
            this.dineroGanado = 100000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Tercero") && categoria.equals("MASTER_1000")) {
            this.dineroGanado = 250000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Tercero") && categoria.equals("MASTER_500")) {
            this.dineroGanado = 150000 + getTenista().getDineroGanado();
            getTenista().setDineroGanado(dineroGanado);
        }
        if (resultado.equals("Tercero") && categoria.equals("MASTER_250"))
            this.dineroGanado = 100000 + getTenista().getDineroGanado();
        getTenista().setDineroGanado(dineroGanado);
    }




}
