package org.sid.cinema_proj;

import org.sid.cinema_proj.entities.Film;
import org.sid.cinema_proj.entities.Salle;
import org.sid.cinema_proj.entities.Ticket;
import org.sid.cinema_proj.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaProjApplication implements CommandLineRunner {
    @Autowired
    private ICinemaInitService iCinemaInitService;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CinemaProjApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // permettre d'inclure l'id dans le resultats JSON
        repositoryRestConfiguration.exposeIdsFor(Film.class,Salle.class, Ticket.class);
        iCinemaInitService.initVilles();
        iCinemaInitService.initCinemas();
        iCinemaInitService.initSalles();
        iCinemaInitService.initPlaces();
        iCinemaInitService.initSeances();
        iCinemaInitService.initCategories();
        iCinemaInitService.initFilms();
        iCinemaInitService.initProjections();
        iCinemaInitService.initTickets();

    }
}
