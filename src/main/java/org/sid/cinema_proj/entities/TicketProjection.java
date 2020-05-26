package org.sid.cinema_proj.entities;

import org.springframework.data.rest.core.config.Projection;
//http://localhost:8086/projectionFilms/1/tickets?projection=ticketProj
@Projection(name = "ticketProj",types = {org.sid.cinema_proj.entities.Ticket.class})
public interface TicketProjection {
    public Long getId();
    public String getNomClient();
    public Double getPrix();
    public Integer getCodePayement();
    public boolean getReservee();
    public Place getPlace();
}
