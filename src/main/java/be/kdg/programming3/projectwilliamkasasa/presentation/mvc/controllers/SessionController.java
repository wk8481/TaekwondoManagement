package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SessionController {

    protected void updatePageVisitHistory(String pageName, HttpSession session) {
        // Get the page visit history from the session
        List<String> pageVisits = (List<String>) session.getAttribute("pageVisits");
        if (pageVisits == null) {
            pageVisits = new ArrayList<>();
        }

        // Add the current page visit to the history
        pageVisits.add(pageName + " - " + new Date());

        // Store the updated page visit history back in the session
        session.setAttribute("pageVisits", pageVisits);
    }

}
