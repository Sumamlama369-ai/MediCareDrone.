package com.MediCareDrone.Controller;

import com.MediCareDrone.DAO.OrderDAO;
import com.MediCareDrone.DAO.ProductDAO;
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
 * Dashboard servlet - handles access to the dashboard.jsp page
 * Includes login authentication using session and cookie, and retrieves data for display
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /Dashboard
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
        String username = null; // This will store the logged-in username

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
                session = request.getSession(); // create new session
                session.setAttribute("username", username);
                System.out.println("Session restored from cookie: " + username);
            }
        }

        // STEP 5: If no session and no cookie, block access and redirect to login
        if (username == null) {
            System.out.println("Unauthorized access to /Dashboard. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Stop further execution
        }

        // STEP 6: Get total registered user count
        UserDAO userDAO = new UserDAO();
        int totalUsers = userDAO.getTotalUserCount();
        request.setAttribute("totalUsers", totalUsers); // send to JSP
        System.out.println("Total users from DAO: " + totalUsers);

        // STEP 7: Get total drone count
        int totalDrones = new ProductDAO().getTotalDroneCount();
        request.setAttribute("totalDrones", totalDrones); // send to JSP
        System.out.println("Total drones from DAO: " + totalDrones);

        // STEP 8: Get total order count
        int totalOrders = new OrderDAO().getTotalOrderCount();
        request.setAttribute("totalOrders", totalOrders); // send to JSP
        System.out.println("Total orders from DAO: " + totalOrders);

        // STEP 9: Get average order value
        double avgOrderValue = new OrderDAO().getAverageOrderValue();
        request.setAttribute("avgOrderValue", avgOrderValue); // send to JSP
        System.out.println("Average order value from DAO: " + avgOrderValue);

        // STEP 10: User is authenticated, pass data to the dashboard JSP
        request.setAttribute("username", username); // Optional: use it in JSP if needed
        request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /Dashboard (same as GET)
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Reuse GET method logic
        doGet(request, response);
    }
}