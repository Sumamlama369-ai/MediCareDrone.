package com.MediCareDrone.Controller;

import com.MediCareDrone.model.UserModel;
import com.MediCareDrone.DAO.UserDAO;
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
 * Portfolio servlet - handles access to the portfolio.jsp page
 * Manages user authentication and updates to user profile information
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Portfolio" })
public class Portfolio extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the user's portfolio
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // STEP 1: Try to retrieve an existing session (do not create new)
        HttpSession session = request.getSession(false);
        String username = null;

        // STEP 2: Check if session exists and contains a valid username
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("User authenticated via session: " + username);
        } else {
            // STEP 3: If no session, check for the "username" cookie
            Cookie usernameCookie = CookieUtil.getCookie(request, "username");
            if (usernameCookie != null) {
                username = usernameCookie.getValue();

                // STEP 4: Recreate session if cookie found
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("Session restored from cookie: " + username);
            }
        }

        // STEP 5: If no session and no cookie, block access and redirect to login
        if (username == null) {
            System.out.println("Unauthorized access to /Portfolio. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // STEP 6: Retrieve user details from database
        try {
            UserDAO userDAO = new UserDAO();
            UserModel user = userDAO.getUserByUsername(username);

            // STEP 7: Check if user exists and pass to JSP
            if (user != null) {
                request.setAttribute("user", user);
                System.out.println("Retrieved user details for: " + username);
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (Exception e) {
            // STEP 8: Handle errors during user retrieval
            e.printStackTrace();
            System.out.println("Error retrieving user details: " + e.getMessage());
        }

        // STEP 9: Forward to portfolio JSP
        request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to update user's first and last name
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // STEP 1: Retrieve form parameters
        String username = request.getParameter("username");
        String updatedFirstName = request.getParameter("firstName");
        String updatedLastName = request.getParameter("lastName");

        // STEP 2: Retrieve user from database
        UserDAO userDAO = new UserDAO();
        UserModel user = userDAO.getUserByUsername(username);

        // STEP 3: Check if user exists and update profile
        if (user != null) {
            // STEP 4: Update user object with new values
            user.setFirstName(updatedFirstName);
            user.setLastName(updatedLastName);

            // STEP 5: Attempt to save updated user to database
            boolean updated = userDAO.updateUser(user);

            // STEP 6: Set appropriate message based on update success
            if (updated) {
                System.out.println("User profile updated successfully for: " + username);
                request.setAttribute("successMessage", "Profile updated successfully.");
            } else {
                System.out.println("Failed to update user profile for: " + username);
                request.setAttribute("errorMessage", "Failed to update profile.");
            }
        } else {
            // STEP 7: Handle case where user is not found
            System.out.println("No user found to update with username: " + username);
            request.setAttribute("errorMessage", "User not found.");
        }

        // STEP 8: Reload portfolio page with updated information
        doGet(request, response);
    }
}