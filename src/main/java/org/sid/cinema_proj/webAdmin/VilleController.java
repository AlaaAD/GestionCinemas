package org.sid.cinema_proj.webAdmin;

import org.sid.cinema_proj.dao.VilleRepository;
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
public class VilleController {
    @Autowired
    private VilleRepository villeRepository;


    @GetMapping(path = "/listVilles")
    public String getVilles(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue ="5") int size,
                            @RequestParam(name = "motCle",defaultValue ="" ) String mc
                            ){
        Page<Ville> pageVilles = villeRepository.findByNameContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageVilles.getTotalPages()]);
        model.addAttribute("pageVilles",pageVilles.getContent());
        return "listVilles";
    }

    @GetMapping(path = "/deleteVille/{id}")
    public String deleteVille(@PathVariable("id") Long id, int page, int size, String motCle){
        villeRepository.deleteById(id);
        return "redirect:/listVilles?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addVille")
    public String addVille(Model model){
        model.addAttribute("ville", new Ville());
        return "addVille";
    }
    @GetMapping("/editVille/{id}")
    public String editVille(Model model,@PathVariable("id") Long id){
        Ville ville = villeRepository.findById(id).get();
        model.addAttribute("ville", ville);
        return "editVille";
    }

    @PostMapping("/saveVille")
    public String saveVille(@Valid Ville ville, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addVille";
        villeRepository.save(ville);

        return "redirect:/listVilles";
    }
    @PostMapping("/editVille/updateVille")
    public String updateVille(@Valid Ville ville, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addVille";
        villeRepository.save(ville);

        return "redirect:/listVilles";
    }


}
