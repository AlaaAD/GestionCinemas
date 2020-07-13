package org.sid.cinema_proj.webAdmin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.sid.cinema_proj.dao.CategorieRepository;
import org.sid.cinema_proj.dao.CinemaRepository;
import org.sid.cinema_proj.dao.FilmRepository;
import org.sid.cinema_proj.dao.VilleRepository;
import org.sid.cinema_proj.entities.Categorie;
import org.sid.cinema_proj.entities.Cinema;
import org.sid.cinema_proj.entities.Film;
import org.sid.cinema_proj.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FilmController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @Value("${dir.images}")
    private String imageDir;


    @GetMapping(path = "/listFilms")
    public String getFilms(Model model,
                             @RequestParam(name = "page",defaultValue = "0") int page,
                             @RequestParam(name = "size",defaultValue ="5") int size,
                             @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Film> pageFilms = filmRepository.findByTitreContains(mc, PageRequest.of(page,size));
        String path = System.getProperty("user.home")+"/cinema/images/";

        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("path",path);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageFilms.getTotalPages()]);
        model.addAttribute("pageFilms",pageFilms.getContent());

        return "listFilms";
    }

    @GetMapping(path = "/deleteFilm/{id}")
    public String deleteFilms(@PathVariable("id") Long id, int page, int size, String motCle){
        filmRepository.deleteById(id);
        return "redirect:/listFilms?page="+page+"&size="+size+"&motCle="+motCle+"";
    }
    @GetMapping("/addFilm")
    public String addFilm(Model model){
        List<Categorie> categories=categorieRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("film", new Film());

        return "addFilm";
    }
    @GetMapping("/editFilm/{id}")
    public String editFilm(Model model,@PathVariable("id") Long id){
        Film film = filmRepository.findById(id).get();
        List<Categorie> categories=categorieRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("film", film);
        return "editFilm";
    }

    @PostMapping("/saveFilm")
    public String saveFilm(@Valid Film film,
                           BindingResult bindingResult, Model model,
                           @RequestParam(name="image")MultipartFile file) throws Exception{
        if(bindingResult.hasErrors()) {
            List<Categorie> categories=categorieRepository.findAll();
            model.addAttribute("categories",categories);
            return "addFilm";
        }
        if(!(file.isEmpty())){film.setPhoto(file.getOriginalFilename());}
        filmRepository.save(film);
        if(!(file.isEmpty())){

            film.setPhoto(file.getOriginalFilename());
            file.transferTo(new File(imageDir+film.getId()));

        }


        return "redirect:/listFilms";
    }
    @PostMapping("/editFilm/updateFilm")
    public String updateFilm(@Valid Film film, BindingResult bindingResult,
                             @RequestParam(name="image")MultipartFile file) throws Exception{
        if(bindingResult.hasErrors()) return "addFilm";

        if(!(file.isEmpty())){film.setPhoto(file.getOriginalFilename());}
        filmRepository.save(film);
        if(!(file.isEmpty())){

            film.setPhoto(file.getOriginalFilename());
            file.transferTo(new File(imageDir+film.getId()));

        }

        return "redirect:/listFilms";
    }

}
