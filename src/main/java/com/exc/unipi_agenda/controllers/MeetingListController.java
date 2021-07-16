package com.exc.unipi_agenda.controllers;

import com.exc.unipi_agenda.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class MeetingListController extends ContextController{
    @GetMapping(path = "/user")
    public Object getContent(Model model, HttpSession session) {

        User registedUser = (User)session.getAttribute("user");
        if(registedUser == null){
            return new RedirectView("/");
        }

//        registedUser.setNotificationList(refreshesNotifications(registedUser.getUsername()));
//        registedUser.setMeetings(refreshesMeetings(registedUser.getUsername()));
//        session.setAttribute("user", registedUser);

        System.out.println(registedUser.getMeetings());
        model.addAttribute("user", registedUser);

        return "meeting-list.html";
    }
}