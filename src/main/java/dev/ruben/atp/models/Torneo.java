package dev.ruben.atp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    @Column(name = "id_sec")
    private Long idSec;
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

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Participante> participantes;

    public void addParticipante(Participante participante) {
        participantes.add(participante);
        participante.setTorneo(this);
    }
    public void removeParticipante(Participante participante) {
        participantes.remove(participante);
        participante.setTorneo(null);
    }








}
