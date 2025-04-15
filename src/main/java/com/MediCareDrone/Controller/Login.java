package com.MediCareDrone.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * LoginServlet handles user authentication.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward to login page if GET request is made
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Processing login request...");

        // Get username and password from request
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Hardcoded validation (replace with DB validation)
        if ("admin".equals(username) && "admin123".equals(password)) {
            System.out.println("Login successful!");

            // Create session
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Redirect to home page
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            System.out.println("Invalid credentials!");

            // Set error message
            req.setAttribute("error", "Invalid username or password.");

            // Forward to login page with error message
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}
