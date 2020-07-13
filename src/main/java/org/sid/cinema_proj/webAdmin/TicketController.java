package org.sid.cinema_proj.webAdmin;

import org.sid.cinema_proj.dao.CinemaRepository;
import org.sid.cinema_proj.dao.PlaceRepository;
import org.sid.cinema_proj.dao.SalleRepository;
import org.sid.cinema_proj.dao.TicketRepository;
import org.sid.cinema_proj.entities.Place;
import org.sid.cinema_proj.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;


    @GetMapping(path = "/listTickets")
    public String getPlaces(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue ="10") int size,
                            @RequestParam(name = "motCle",defaultValue ="" ) String mc
    ){
        Page<Ticket> pageTickets = ticketRepository.findByNomClientContains(mc, PageRequest.of(page,size));
        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("motCle",mc);
        model.addAttribute("pages",new int[pageTickets.getTotalPages()]);
        model.addAttribute("pageTickets",pageTickets.getContent());

        return "listTickets";
    }


}


