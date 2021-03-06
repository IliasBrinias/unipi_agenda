package com.exc.unipi_agenda.controllers;

//import com.exc.unipi_agenda.model.Db;
import com.exc.unipi_agenda.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path = "/")
public class LoginController extends ContextController{

    @GetMapping(path = "/")
    public String getContent(Model model, HttpSession session) {
        if(this.isDesktop(session)){
            return "index-desktop";
        }

        return "index";
    }

    @PostMapping("/")
    public Object loginUser(Model model,
                            HttpSession session,
                            @RequestParam(name = "username", required = false) String username,
                            @RequestParam(name = "password", required = false) String password)
    {
        User registeredUser = User.login(username,password,model);
        if (registeredUser != null){
            registeredUser.setNotificationList(refreshesNotifications(registeredUser.getUsername()));
            registeredUser.setMeetings(refreshesMeetings(registeredUser.getUsername()));
            session.setAttribute("user", registeredUser);

            return new RedirectView("user");
        }

        if(this.isDesktop(session)){
            return "index-desktop";
        }

        return "index";
    }
}