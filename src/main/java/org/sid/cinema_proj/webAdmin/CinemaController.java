package org.sid.cinema_proj.webAdmin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.sid.cinema_proj.dao.CinemaRepository;
import org.sid.cinema_proj.dao.VilleRepository;
import org.sid.cinema_proj.entities.Cinema;
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
public class CinemaController {
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private  VilleRepository villeRepository;


    @GetMapping(path = "/listCinemas")
    public String getCinemas(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue ="5") int size,
                            @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Cinema> pageCinemas = cinemaRepository.findByNameContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageCinemas.getTotalPages()]);
        model.addAttribute("pageCinemas",pageCinemas.getContent());

        return "listCinemas";
    }

    @GetMapping(path = "/deleteCinema/{id}")
    public String deleteCinemas(@PathVariable("id") Long id, int page, int size, String motCle){
        cinemaRepository.deleteById(id);
        return "redirect:/listCinemas?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addCinema")
    public String addCinema(Model model){
        List<Ville> villes=villeRepository.findAll();
        model.addAttribute("villes",villes);
        model.addAttribute("cinema", new Cinema());

        return "addCinema";
    }
    @GetMapping("/editCinema/{id}")
    public String editCinema(Model model,@PathVariable("id") Long id){
        Cinema cinema = cinemaRepository.findById(id).get();
        List<Ville> villes=villeRepository.findAll();
        model.addAttribute("villes",villes);
        model.addAttribute("cinema", cinema);
        return "editCinema";
    }

    @PostMapping("/saveCinema")
    public String saveCinema(@Valid Cinema cinema, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            List<Ville> villes=villeRepository.findAll();
            model.addAttribute("villes",villes);
            return "addCinema";
        }
        cinemaRepository.save(cinema);

        return "redirect:/listCinemas";
    }
    @PostMapping("/editCinema/updateCinema")
    public String updateCinema(@Valid Cinema cinema, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addCinema";
        cinemaRepository.save(cinema);

        return "redirect:/listCinemas";
    }
}
