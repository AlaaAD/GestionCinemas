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
public class PlaceController {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;


    @GetMapping(path = "/listPlaces")
    public String getPlaces(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue ="10") int size,
                            @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Place> pagePlaces = placeRepository.findBySalleNameContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pagePlaces.getTotalPages()]);
        model.addAttribute("pagePlaces",pagePlaces.getContent());

        return "listPlaces";
    }


}
