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

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles GET requests to show the login form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forwarding user to the login page JSP
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    // Handles POST requests when the login form is submitted
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Retrieve username and password entered by user
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Step 2: Input validation — check if fields are empty
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            // If either field is empty, show error message and redirect back to login page
            request.setAttribute("error", "Please enter both username and password.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // Step 3: Start DB connection and query user by username only
        try (Connection conn = DbConfig.getDbConnection()) {

            // SQL query to select the user's encrypted password based on username
            String sql = "SELECT password FROM User_Details WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            // Step 4: Check if the username exists in the database
            if (rs.next()) {
                // Retrieve the stored encrypted password
                String storedPassword = rs.getString("password");

                // Hash the input password to compare with stored hash
                String hashedInputPassword = Hashing.hashPassword(password);

                // Step 5: Check if the stored password matches the one entered by the user
                if (storedPassword != null && storedPassword.equals(hashedInputPassword)) {
                    // Step 6: Password match — login success

                    // Create session and store username in the session for future use
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username); // Store username in session

                    // (Optional) Create a persistent cookie to store the username (for auto-login, etc.)
                    Cookie usernameCookie = new Cookie("username", username);
                    usernameCookie.setMaxAge(60 * 60 * 24 * 7); // Set cookie expiration to 7 days
                    response.addCookie(usernameCookie);

                    // Redirect the user to the home or dashboard page after successful login
                    response.sendRedirect(request.getContextPath() + "/home1");
                    System.out.println("Login successful! Redirecting to /home1...");

                    return;
                } else {
                    //  Step 7: Password mismatch — show error message and redirect to login page
                    request.setAttribute("error", "Invalid username or password.");
                    request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                    return;
                }
            } else {
                //  Step 8: Username not found in database — show error message
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            }

        } catch (SQLException | ClassNotFoundException e) {
            //  Handle any SQL or connection exceptions and display an error message to the user
            e.printStackTrace();
            request.setAttribute("error", "Internal server error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
