package com.MediCareDrone.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 23048591SumanLama
 * This is the home servlet that routes /home
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home1", "/" })
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static boolean isDatabaseConnected() {
        String jdbcURL = "jdbc:mysql://localhost:3306/demo1?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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
        boolean dbStatus = isDatabaseConnected();
        request.setAttribute("dbStatus", dbStatus ? "✅ Connected to Database" : "❌ Failed to Connect to Database");
        request.getRequestDispatcher("/WEB-INF/pages/home1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
