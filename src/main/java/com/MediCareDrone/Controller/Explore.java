package com.MediCareDrone.Controller;

import com.MediCareDrone.DAO.ProductDAO;
import com.MediCareDrone.model.ProductModel;
import com.MediCareDrone.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * @author 23048591 Suman Lama
 * Explore servlet - handles access to the explore.jsp page
 * Includes session and cookie authentication, and supports drone search functionality
 */
@WebServlet("/Explore")
public class Explore extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    /**
     * Initializes the servlet by creating a ProductDAO instance
     */
    @Override
    public void init() {
        // STEP 1: Initialize the ProductDAO for database operations
        productDAO = new ProductDAO();
    }

    /**
     * Handles GET requests to /Explore
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // STEP 2: Try to retrieve an existing session (do not create new)
        HttpSession session = request.getSession(false);
        String username = null;

        // STEP 3: Check if session exists and contains a valid username
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("User authenticated via session: " + username);
        } else {
            // STEP 4: If no session, check for the "username" cookie
            Cookie usernameCookie = CookieUtil.getCookie(request, "username");
            if (usernameCookie != null) {
                username = usernameCookie.getValue();

                // STEP 5: Recreate session if cookie found
                session = request.getSession(); // create new session
                session.setAttribute("username", username);
                System.out.println("Session restored from cookie: " + username);
            }
        }

        // STEP 6: If no session and no cookie, block access and redirect to login
        if (username == null) {
            System.out.println("Unauthorized access to /Explore. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return; // Stop further execution
        }

        // STEP 7: Handle search query from search bar
        String searchQuery = request.getParameter("query");
        List<ProductModel> products;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // STEP 8: Search for products by name if query is provided
            products = productDAO.searchProductsByName(searchQuery.trim());
            System.out.println("Search query: " + searchQuery);
        } else {
            // STEP 9: Retrieve all products if no search query
            products = productDAO.getAllProducts();
            System.out.println("Showing all products");
        }

        // STEP 10: Pass data to JSP
        request.setAttribute("username", username); // Optional: use it in JSP if needed
        request.setAttribute("products", products); // Send product list to JSP
        request.setAttribute("searchQuery", searchQuery); // Optional: retain search query in input box
        System.out.println("Retrieved product list, size: " + products.size());

        // STEP 11: Forward to explore JSP
        request.getRequestDispatcher("/WEB-INF/pages/explore.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /Explore (same as GET)
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