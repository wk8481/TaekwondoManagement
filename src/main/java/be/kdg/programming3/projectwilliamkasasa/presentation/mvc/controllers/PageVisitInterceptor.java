package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PageVisitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get the page visit history from the session
        HttpSession session = request.getSession();
        List<String> pageVisits = (List<String>) session.getAttribute("pageVisits");
        if (pageVisits == null) {
            pageVisits = new ArrayList<>();
        }

        // Add the current page visit to the history
        pageVisits.add(request.getRequestURI() + " - " + new Date());

        // Store the updated page visit history back in the session
        session.setAttribute("pageVisits", pageVisits);

        return true;
    }
}
