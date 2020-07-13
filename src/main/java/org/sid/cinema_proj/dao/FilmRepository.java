package org.sid.cinema_proj.dao;

import org.sid.cinema_proj.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface FilmRepository extends JpaRepository<Film,Long> {

    Page<Film> findByTitreContains(String titre, Pageable pageable);
}
