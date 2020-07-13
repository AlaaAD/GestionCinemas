package org.sid.cinema_proj.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ProjectionFilm implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Double prix;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Salle salle;
    private Date dateProjection;
    @ManyToOne
    private Film film;
    @ManyToOne
    private Seance seance;
    @OneToMany(mappedBy = "projection")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Ticket> tickets;



}
