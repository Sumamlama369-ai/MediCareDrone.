package com.MediCareDrone.Controller;

import com.MediCareDrone.config.DbConfig;
import com.MediCareDrone.Controller.Hashing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 23048591 Suman Lama
 * Login servlet - handles user authentication for the login page
 * Processes login form submissions, validates credentials, and manages session/cookie
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the login form
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // STEP 1: Forward the request to the login JSP page
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to process login form submissions
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // STEP 1: Retrieve username and password from form submission
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // STEP 2: Validate input fields
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            // STEP 3: If fields are empty, set error message and forward to login page
            request.setAttribute("error", "Please enter both username and password.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // STEP 4: Establish database connection and query user
        try (Connection conn = DbConfig.getDbConnection()) {

            // STEP 5: Prepare SQL query to retrieve stored password for the username
            String sql = "SELECT password FROM User_Details WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // STEP 6: Execute query and process result
            ResultSet rs = stmt.executeQuery();

            // STEP 7: Check if username exists in the database
            if (rs.next()) {
                // STEP 8: Retrieve stored hashed password
                String storedPassword = rs.getString("password");

                // STEP 9: Hash the input password for comparison
                String hashedInputPassword = Hashing.hashPassword(password);

                // STEP 10: Compare stored password with hashed input password
                if (storedPassword != null && storedPassword.equals(hashedInputPassword)) {
                    // STEP 11: Successful login - create session and store username
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                    // STEP 12: Create a persistent cookie for auto-login
                    Cookie usernameCookie = new Cookie("username", username);
                    usernameCookie.setMaxAge(60 * 60 * 24 * 7); // 7-day expiration
                    response.addCookie(usernameCookie);

                    // STEP 13: Redirect to home page
                    response.sendRedirect(request.getContextPath() + "/home1");
                    System.out.println("Login successful! Redirecting to /home1...");
                    return;
                } else {
                    // STEP 14: Password mismatch - set error message and forward to login page
                    request.setAttribute("error", "Invalid username or password.");
                    request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                    return;
                }
            } else {
                // STEP 15: Username not found - set error message and forward to login page
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            }

        } catch (SQLException | ClassNotFoundException e) {
            // STEP 16: Handle database or connection errors
            e.printStackTrace();
            request.setAttribute("error", "Internal server error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}