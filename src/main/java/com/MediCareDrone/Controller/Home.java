package com.MediCareDrone.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.MediCareDrone.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author 23048591 Suman Lama
 * Home servlet - handles access to the home1.jsp page
 * Includes session and cookie authentication, and checks database connection status
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home1" })
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Checks the database connection status
     *
     * @return true if the database connection is successful, false otherwise
     */
    public static boolean isDatabaseConnected() {
        // STEP 1: Define database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/demo1?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "";

        try {
            // STEP 2: Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // STEP 3: Attempt to establish database connection
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            if (conn != null && !conn.isClosed()) {
                // STEP 4: Confirm connection success
                System.out.println("Database connection successful.");
                return true;
            }
        } catch (ClassNotFoundException e) {
            // STEP 5: Handle missing JDBC driver
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            // STEP 6: Handle database connection failure
            System.err.println("Database connection failed: " + e.getMessage());
        }
        // STEP 7: Return false if connection fails
        return false;
    }

    /**
     * Handles GET requests to /home1
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
            System.out.println("User authenticated via session: " + username);
        } else {
            // STEP 3: If no session, check for the "username" cookie
            Cookie usernameCookie = CookieUtil.getCookie(request, "username");
            if (usernameCookie != null) {
                username = usernameCookie.getValue();

                // STEP 4: Recreate session if cookie found
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("Session recreated from cookie: " + username);
            }
        }

        // STEP 5: If no session and no cookie, block access and redirect to login
        if (username == null) {
            System.out.println("No active session or cookie found. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Stop further processing
        }

        // STEP 6: Check database connection status
        boolean dbStatus = isDatabaseConnected();

        // STEP 7: Pass data to JSP
        request.setAttribute("dbStatus", dbStatus ? "Connected to Database" : "Failed to Connect to Database");
        request.setAttribute("username", username); // Optional: use it in JSP for personalization
        System.out.println("Database status: " + (dbStatus ? "Connected" : "Not Connected"));

        // STEP 8: Forward to home JSP
        request.getRequestDispatcher("/WEB-INF/pages/home1.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /home1 (same as GET)
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // STEP 1: Reuse GET method logic for POST requests
        doGet(request, response);
    }
}