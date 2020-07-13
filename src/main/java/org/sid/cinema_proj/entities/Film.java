package org.sid.cinema_proj.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Film implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String titre;
    private Double duree;
    private String realisateur;
    private String description;
    private String photo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateSortie;
    @ManyToOne
    private Categorie categorie;
   @OneToMany(mappedBy = "film")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private Collection<ProjectionFilm> projections;


}
