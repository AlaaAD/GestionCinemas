package org.sid.cinema_proj.dao;

import org.sid.cinema_proj.entities.Cinema;
import org.sid.cinema_proj.entities.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository extends JpaRepository<Cinema,Long> {
    public Page<Cinema> findByNameContains(String mc, Pageable pageable);
    public List<Cinema> findByVille(Ville ville);
}
