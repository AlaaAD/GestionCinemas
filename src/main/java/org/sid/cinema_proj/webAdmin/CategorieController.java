package org.sid.cinema_proj.webAdmin;

import org.sid.cinema_proj.dao.CategorieRepository;
import org.sid.cinema_proj.dao.VilleRepository;
import org.sid.cinema_proj.entities.Categorie;
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
public class CategorieController {
    @Autowired
    private CategorieRepository categorieRepository;


    @GetMapping(path = "/listCategories")
    public String getCategories(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue ="5") int size,
                            @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Categorie> pageCategories = categorieRepository.findByNameContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageCategories.getTotalPages()]);
        model.addAttribute("pageCategories",pageCategories.getContent());
        return "listCategories";
    }

    @GetMapping(path = "/deleteCategorie/{id}")
    public String deleteCategorie(@PathVariable("id") Long id, int page, int size, String motCle){
        categorieRepository.deleteById(id);
        return "redirect:/listCategories?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addCategorie")
    public String addCategorie(Model model){
        model.addAttribute("categorie", new Categorie());
        return "addCategorie";
    }
    @GetMapping("/editCategorie/{id}")
    public String editCategorie(Model model,@PathVariable("id") Long id){
        Categorie categorie = categorieRepository.findById(id).get();
        model.addAttribute("categorie", categorie);
        return "editCategorie";
    }

    @PostMapping("/saveCategorie")
    public String saveCategorie(@Valid Categorie categorie, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addCategorie";
        categorieRepository.save(categorie);

        return "redirect:/listCategories";
    }
    @PostMapping("/editCategorie/updateCategorie")
    public String updateCategorie(@Valid Categorie categorie, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "addCategorie";
        categorieRepository.save(categorie);

        return "redirect:/listCategories";
    }


}
