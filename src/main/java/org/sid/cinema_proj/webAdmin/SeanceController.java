package org.sid.cinema_proj.webAdmin;

import org.sid.cinema_proj.dao.CategorieRepository;
import org.sid.cinema_proj.dao.SeanceRepository;
import org.sid.cinema_proj.dao.VilleRepository;
import org.sid.cinema_proj.entities.Categorie;
import org.sid.cinema_proj.entities.Seance;
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

@Controller
public class SeanceController {
    @Autowired
    private SeanceRepository seanceRepository;


    @GetMapping(path = "/listSeances")
    public String getSeances(Model model,
                                @RequestParam(name = "page",defaultValue = "0") int page,
                                @RequestParam(name = "size",defaultValue ="10") int size,
                                @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Seance> pageSeances = seanceRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageSeances.getTotalPages()]);
        model.addAttribute("pageSeances",pageSeances.getContent());
        return "listSeances";
    }

    @GetMapping(path = "/deleteSeance/{id}")
    public String deleteSeance(@PathVariable("id") Long id, int page, int size, String motCle){
        seanceRepository.deleteById(id);
        return "redirect:/listSeances?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addSeance")
    public String addSeance(Model model){
        model.addAttribute("seance", new Seance());
        return "addSeance";
    }
    @GetMapping("/editSeance/{id}")
    public String editSeance(Model model,@PathVariable("id") Long id){
        Seance seance = seanceRepository.findById(id).get();
        model.addAttribute("seance", seance);
        return "editSeance";
    }

    @PostMapping("/saveSeance")
    public String saveSeance(@Valid Seance seance, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addSeance";
        seanceRepository.save(seance);

        return "redirect:/listSeances";
    }
    @PostMapping("/editSeance/updateSeance")
    public String updateSeance(@Valid Seance seance, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addSeance";
        seanceRepository.save(seance);

        return "redirect:/listSeances";
    }


}
