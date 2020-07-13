package org.sid.cinema_proj.webAdmin;

import org.sid.cinema_proj.dao.CinemaRepository;
import org.sid.cinema_proj.dao.PlaceRepository;
import org.sid.cinema_proj.dao.SalleRepository;
import org.sid.cinema_proj.entities.Cinema;
import org.sid.cinema_proj.entities.Place;
import org.sid.cinema_proj.entities.Salle;
import org.sid.cinema_proj.entities.Ville;
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
import java.util.List;

@Controller
public class SalleController {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;


    @GetMapping(path = "/listSalles")
    public String getSalles(Model model,
                             @RequestParam(name = "page",defaultValue = "0") int page,
                             @RequestParam(name = "size",defaultValue ="5") int size,
                             @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Salle> pageSalles = salleRepository.findByNameContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageSalles.getTotalPages()]);
        model.addAttribute("pageSalles",pageSalles.getContent());

        return "listSalles";
    }

    @GetMapping(path = "/deleteSalle/{id}")
    public String deleteSalles(@PathVariable("id") Long id, int page, int size, String motCle){
        salleRepository.deleteById(id);
        return "redirect:/listSalles?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addSalle")
    public String addSalle(Model model){
        List<Cinema> cinemas=cinemaRepository.findAll();
        model.addAttribute("cinemas",cinemas);
        model.addAttribute("salle", new Salle());

        return "addSalle";
    }
    @GetMapping("/editSalle/{id}")
    public String editSalle(Model model,@PathVariable("id") Long id){
        Salle salle = salleRepository.findById(id).get();
        List<Cinema> cinemas=cinemaRepository.findAll();
        model.addAttribute("cinemas",cinemas);
        model.addAttribute("salle", salle);
        return "editSalle";
    }

    @PostMapping("/saveSalle")
    public String saveSalle(@Valid Salle salle, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()) {
            List<Cinema> cinemas=cinemaRepository.findAll();
            model.addAttribute("cinemas",cinemas);
            return "addSalle"; }
        salleRepository.save(salle);

        return "redirect:/listSalles";
    }
    @PostMapping("/editSalle/updateSalle")
    public String updateSalle(@Valid Salle salle, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addSalle";
        salleRepository.save(salle);

        return "redirect:/listSalles";
    }

    @GetMapping(path = "/generatePlaces/{id}")
    public String generatePlaces(@PathVariable("id") Long id){
        Salle salle= salleRepository.findById(id).get();
        int nombreDePlaces = salle.getNombrePlaces();
        // Tester si on a deja generet les places si non ajouter
          if(!placeRepository.existsPlaceBySalleName(salle.getName())){
              for(int i=0;i<nombreDePlaces;i++){
                  Place place = new Place();
                  place.setNumero(i+1);
                  place.setSalle(salle);
                  placeRepository.save(place);
              }
          }

        return "redirect:/listPlaces";
    }
}
