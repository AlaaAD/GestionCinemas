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
public class Seance implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull
    private Date heureDebut;
    @OneToMany(mappedBy = "seance")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<ProjectionFilm> projections;




}
