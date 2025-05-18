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
 * 23048591SumanLama
 * This is the home servlet that routes /home1 or /
 * Now enhanced to check session and cookie for login access
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home1" })
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Utility method to check database connection
    public static boolean isDatabaseConnected() {
        String jdbcURL = "jdbc:mysql://localhost:3306/demo1?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Attempt to connect
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection successful.");
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // STEP 1: Check if session exists and contains "username"
        HttpSession session = request.getSession(false); // false = don't create if not exist
        String username = null;

        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("User authenticated via session: " + username);
        } else {
            // 🟨 Optional fallback: Try checking cookie for username
            Cookie usernameCookie = CookieUtil.getCookie(request, "username");
            if (usernameCookie != null) {
                username = usernameCookie.getValue();

                // Restore session if found in cookie (optional)
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("Session recreated from cookie: " + username);
            }
        }

        // If user is not authenticated, redirect to login page
        if (username == null) {
            System.out.println("No active session or cookie found. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Stop further processing
        }

        // ✅ STEP 2: User is authenticated, check DB connection
        boolean dbStatus = isDatabaseConnected();

        // Pass info to JSP page
        request.setAttribute("dbStatus", dbStatus ? "✅ Connected to Database" : "❌ Failed to Connect to Database");
        request.setAttribute("username", username); // pass username to welcome user

        // Forward to home page
        request.getRequestDispatcher("/WEB-INF/pages/home1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Let POST behave same as GET
    }
}
