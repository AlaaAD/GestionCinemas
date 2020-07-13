package org.sid.cinema_proj.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Cinema implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min=4, max = 15)
    private String name;
    private Double longitude,latitude,altitude;
    @NotNull
    private int nombreSalles;
    @ManyToOne
    private Ville ville;
    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles;
}
