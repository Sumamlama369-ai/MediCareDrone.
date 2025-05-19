package com.MediCareDrone.Controller;

import com.MediCareDrone.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author 23048591 Suman Lama
 * Servlet implementation for the Contact Us page.
 * Handles requests for displaying the 'Contact Us' page.
 */
@WebServlet("/ContactUs")
public class ContactUs extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /ContactUs
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // STEP 1: Try retrieving existing session (without creating new one)
        HttpSession session = request.getSession(false);
        String username = null;

        // STEP 2: If session exists, check if user is logged in
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("User authenticated via session: " + username);
        } else {
            // STEP 3: No session found, so check for 'username' cookie
            Cookie usernameCookie = CookieUtil.getCookie(request, "username");
            if (usernameCookie != null) {
                username = usernameCookie.getValue();

                // STEP 4: If cookie is found, restore the session
                session = request.getSession(); // creates a new session
                session.setAttribute("username", username);
                System.out.println("Session restored from cookie: " + username);
            }
        }

        // STEP 5: If username still null, user isn't logged in
        if (username == null) {
            System.out.println("Unauthorized access to /ContactUs. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // STEP 6: User is authenticated. Proceed to contact page
        request.setAttribute("username", username); // optional: for personalization
        request.getRequestDispatcher("/WEB-INF/pages/contactUs.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /ContactUs
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Use GET logic for POST as well (common in display pages)
        doGet(request, response);
    }
}