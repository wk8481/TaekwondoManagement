package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Controller // MVC controller
@RequestMapping("/taekwondo")
public class Controller extends SessionController {
    private Logger logger = LoggerFactory.getLogger(Controller.class);

    //


    @GetMapping
    public String showHomeView(Model model, HttpSession session) {
        logger.info("Request for home view!");
        model.addAttribute("date", LocalDate.now());
        updatePageVisitHistory("home", session);
        return "home";
    }


    @GetMapping("/sessionhistory")
    public String showSessionHistory(Model model, HttpSession session) {
        List<String> pageVisits = (List<String>) session.getAttribute("pageVisits");
        model.addAttribute("pageVisits", pageVisits);
        return "sessionhistory";
    }







}



