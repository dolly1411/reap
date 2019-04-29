package com.ttn.reap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SessionController {
    
    @PostMapping("/setSessionDates")
    @ResponseBody
    public String setSessionDates(@RequestParam String start, @RequestParam String end, HttpSession session){
        session.setAttribute("startDate",start);
        session.setAttribute("endDate",end);
        return "redirect:/user";
    }
}
