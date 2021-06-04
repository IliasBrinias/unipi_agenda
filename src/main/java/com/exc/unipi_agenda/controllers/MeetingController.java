package com.exc.unipi_agenda.controllers;

import com.exc.unipi_agenda.model.Meeting;
import com.exc.unipi_agenda.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MeetingController {
    @GetMapping("/meeting")
    public Object getContent(Model model, HttpSession session) {

        User registedUser = (User)session.getAttribute("user");
        if(registedUser == null){
            return new RedirectView("/");
        }

        model.addAttribute("user", registedUser);

        return "meeting";
    }

    @PostMapping("/meeting")
    public Object createMeeting(Model model,
                                HttpSession session,
                                @RequestParam(name = "meeting-title", required = false) String meetingTitle,
                                @RequestParam(name = "meeting-desc", required = false) String meetingDescription,
                                @RequestParam(name = "meeting-date", required = false) String meetingDate,
                                @RequestParam(name = "meeting-duration", required = false) String meetingDuration,
                                @RequestParam(name = "meeting-participants", required = false) String meetingParticipants
    )
    {
        User registedUser = (User)session.getAttribute("user");
        if(registedUser == null){
            return new RedirectView("/");
        }

        Meeting newMeeting = new Meeting((String)Meeting.getNewId(), registedUser.get_Username());
        newMeeting.setName(meetingTitle);
//        newMeeting.setDatetime(meetingDate);
//        newMeeting.set(meetingDate);

        String[] meetingParticipantsList = meetingParticipants.split("__separator__");
        String participantUsername = "";
        for (int i=0; i<meetingParticipantsList.length; i++){
            participantUsername = meetingParticipantsList[i];
            newMeeting.addParticipant(participantUsername);
        }


//        String date_string = "2100-09-26 10:00:00";
//        //Instantiating the SimpleDateFormat class
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //Parsing the given String to Date object
//        Date date = formatter.parse(date_string);
//
//        a.createMeeting("meeting test 132", date_string, 12.5f,m);

        model.addAttribute("user", registedUser);
        return "index";
    }
}
