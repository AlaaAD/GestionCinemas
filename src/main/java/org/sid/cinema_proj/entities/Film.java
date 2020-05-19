package org.sid.cinema_proj.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Film implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private Double duree;
    private String realisateur;
    private String description;
    private String photo;
    private Date DateSortie;
    @ManyToOne
    private Categorie categorie;
   @OneToMany(mappedBy = "film")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private Collection<Projection> projections;


}
