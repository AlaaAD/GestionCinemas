package org.sid.cinema_proj.dao;

import org.sid.cinema_proj.entities.Categorie;
import org.sid.cinema_proj.entities.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

    public Page<Categorie> findByNameContains(String mc, Pageable pageable);
}
