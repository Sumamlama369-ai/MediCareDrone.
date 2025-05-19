package com.MediCareDrone.Controller;

import com.MediCareDrone.DAO.UserDAO;
import com.MediCareDrone.model.UserModel;
import com.MediCareDrone.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * @author 23048591 Suman Lama
 * UserDetails servlet - handles access to the userDetails.jsp page
 * Manages user authentication and CRUD operations for user data
 */
@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
    private UserDAO userDAO;

    /**
     * Initializes the servlet by creating a UserDAO instance
     */
    @Override
    public void init() {
        // STEP 1: Initialize UserDAO for database operations
        userDAO = new UserDAO();
    }

    /**
     * Handles GET requests to display the user list
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // STEP 1: Try to retrieve an existing session (do not create new)
        HttpSession session = request.getSession(false);
        String username = null;

        // STEP 2: Check if session exists and contains a valid username
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("Session active for: " + username);
        } else {
            // STEP 3: If no session, check for the "username" cookie
            Cookie cookie = CookieUtil.getCookie(request, "username");
            if (cookie != null) {
                username = cookie.getValue();

                // STEP 4: Recreate session if cookie found
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("Session restored from cookie: " + username);
            }
        }

        // STEP 5: If no session and no cookie, block access and redirect to login
        if (username == null) {
            System.out.println("No session or cookie found. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // STEP 6: Fetch all users from the database
        List<UserModel> users = userDAO.getAllUsers();
        System.out.println("Retrieved user list, size: " + users.size());

        // STEP 7: Pass data to JSP
        request.setAttribute("users", users);
        request.setAttribute("username", username);

        // STEP 8: Forward to userDetails JSP
        request.getRequestDispatcher("/WEB-INF/pages/userDetails.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for adding, updating, or deleting users
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // STEP 1: Retrieve action parameter from form
        String action = request.getParameter("action");

        // STEP 2: Create UserModel from form parameters
        UserModel user = new UserModel(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("phone"),
                request.getParameter("username"),
                request.getParameter("gender"),
                request.getParameter("password")
        );

        // STEP 3: Process the requested action
        switch (action) {
            case "add":
                // STEP 4: Add new user to the database
                userDAO.addUser(user);
                System.out.println("Added user: " + user.getUsername());
                break;
            case "update":
                // STEP 5: Update existing user in the database
                userDAO.updateUser(user);
                System.out.println("Updated user: " + user.getUsername());
                break;
            case "delete":
                // STEP 6: Delete user from the database
                userDAO.deleteUser(user.getUsername());
                System.out.println("Deleted user: " + user.getUsername());
                break;
        }

        // STEP 7: Redirect to UserDetails page after action
        response.sendRedirect(request.getContextPath() + "/UserDetails");
    }
}