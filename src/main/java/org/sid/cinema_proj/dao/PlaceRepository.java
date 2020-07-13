package org.sid.cinema_proj.dao;

import org.sid.cinema_proj.entities.Cinema;
import org.sid.cinema_proj.entities.Place;
import org.sid.cinema_proj.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface PlaceRepository extends JpaRepository<Place,Long> {

    public Page<Place> findBySalleNameContains(String name,Pageable pageable);

    public boolean existsPlaceBySalleName(String name);

}
