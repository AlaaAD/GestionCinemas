package org.sid.cinema_proj.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {
    @RequestMapping(value = "/notAuthorized", method = { RequestMethod.GET, RequestMethod.POST })
    public String notAuthorized(){
        return "notAuthorized";
    }

    @RequestMapping(value = "/login", method =  RequestMethod.GET)
    public String login(){
        return "login";
    }
}
