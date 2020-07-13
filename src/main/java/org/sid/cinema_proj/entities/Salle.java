package org.sid.cinema_proj.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Salle implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min=4, max = 15)
    private String name;
    private int nombrePlaces;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cinema cinema;
    @OneToMany(mappedBy = "salle")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Place> places;
    @OneToMany(mappedBy = "salle")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<ProjectionFilm> projections;

}
