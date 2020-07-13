package org.sid.cinema_proj.dao;

import org.sid.cinema_proj.entities.Cinema;
import org.sid.cinema_proj.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface SalleRepository extends JpaRepository<Salle,Long> {
    public Page<Salle> findByNameContains(String mc, Pageable pageable);
    public List<Cinema> findByCinema(Cinema cinema);
}
