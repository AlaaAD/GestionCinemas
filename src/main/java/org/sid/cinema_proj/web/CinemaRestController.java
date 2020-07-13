package org.sid.cinema_proj.web;

import lombok.Data;
import org.sid.cinema_proj.dao.CinemaRepository;
import org.sid.cinema_proj.dao.FilmRepository;
import org.sid.cinema_proj.dao.TicketRepository;
import org.sid.cinema_proj.dao.VilleRepository;
import org.sid.cinema_proj.entities.Cinema;
import org.sid.cinema_proj.entities.Film;
import org.sid.cinema_proj.entities.Ticket;
import org.sid.cinema_proj.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Value("${dir.images}")
    private String imageDir;

    /*
    @GetMapping("/listFilms")
    public List<Film> listFilms(){
        return filmRepository.findAll();
    }

     */
    @GetMapping(path = "/loadCinema/{id}")
    public List<Cinema> getCinemas(@PathVariable("id")Long id){
        Ville ville = villeRepository.findById(id).get();
        return cinemaRepository.findByVille(ville);
    }

    @GetMapping(path="/imageFilm/{id}",produces= MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name="id")Long id) throws Exception {
        Film f=filmRepository.findById(id).get();
        String photoName=f.getPhoto();
     //   File file=new File(imageDir+photoName);
        // les images enregistrer avec le id
      File file=new File(imageDir+f.getId());
        Path path= Paths.get(file.toURI());
        return Files.readAllBytes(path);

    }
    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketFrom) {
        List<Ticket> listTickets=new ArrayList<>();
        ticketFrom.getTickets().forEach(idTicket->{
//System.out.println(idTicket);
            Ticket ticket=ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketFrom.getNomClient());
            ticket.setReservee(true);
            ticket.setCodePayement(ticketFrom.getCodePayement());
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }


}
@Data
class TicketForm{
    private String nomClient;
    private int codePayement;
    private List<Long> tickets=new ArrayList<>();
}
