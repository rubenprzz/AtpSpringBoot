package dev.ruben.atp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Torneo {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    String ubicacion;
    @Column
    @Enumerated(EnumType.STRING)
    Tipo tipo;
    @Column


}
