package org.sid.cinema_proj.webAdmin;

import org.sid.cinema_proj.dao.*;
import org.sid.cinema_proj.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
@Controller
public class ProjectionFilmController {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionFilmRepository projectionFilmRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;


    @GetMapping(path = "/listProjections")
    public String getProjections(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue ="5") int size,
                            @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<ProjectionFilm> pageProjections = projectionFilmRepository.findByFilmTitreContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageProjections.getTotalPages()]);
        model.addAttribute("pageProjections",pageProjections.getContent());

        return "listProjections";
    }

    @GetMapping(path = "/deleteProjection/{id}")
    public String deleteProjections(@PathVariable("id") Long id, int page, int size, String motCle){
        projectionFilmRepository.deleteById(id);
        return "redirect:/listProjections?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addProjection")
    public String addProjection(Model model){
        List<Seance> seances=seanceRepository.findAll();
        model.addAttribute("seances",seances);
        List<Film> films=filmRepository.findAll();
        model.addAttribute("films",films);
        List<Ville> villes =villeRepository.findAll();
        model.addAttribute("villes",villes);
        model.addAttribute("projection", new ProjectionFilm());

        return "addProjection";
    }
    @GetMapping("/editProjection/{id}")
    public String editProjection(Model model,@PathVariable("id") Long id){
        ProjectionFilm projectionFilm = projectionFilmRepository.findById(id).get();
        List<Seance> seances=seanceRepository.findAll();
        model.addAttribute("seances",seances);
        List<Film> films=filmRepository.findAll();
        model.addAttribute("films",films);
        model.addAttribute("projection", projectionFilm);
        return "editProjection";
    }

    @PostMapping("/saveProjection")
    public String saveProjection(@Valid ProjectionFilm projectionFilm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            List<Seance> seances=seanceRepository.findAll();
            model.addAttribute("seances",seances);
            List<Film> films=filmRepository.findAll();
            model.addAttribute("films",films);

            return "addProjection"; }
        projectionFilmRepository.save(projectionFilm);

        return "redirect:/listProjections";
    }
    @PostMapping("/editProjection/updateProjection")
    public String updateProjection(@Valid ProjectionFilm projectionFilm, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addProjection";
        projectionFilmRepository.save(projectionFilm);

        return "redirect:/listProjections";
    }

    @GetMapping(path = "/generateTickets/{id}")
    public String generateTickets(@PathVariable("id") Long id){
        ProjectionFilm projectionFilm= projectionFilmRepository.findById(id).get();
        int nombreDePlaces = projectionFilm.getSalle().getNombrePlaces();
        Salle salle = projectionFilm.getSalle();
        Collection<Place> places = salle.getPlaces();
        places.forEach(place -> {
            Ticket ticket = new Ticket();
            ticket.setPlace(place);
            ticket.setPrix(projectionFilm.getPrix());
            ticket.setNomClient("Inconnue");
            ticketRepository.save(ticket);
        });



        return "redirect:/listTickets";
    }


}
