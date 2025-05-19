package com.MediCareDrone.Controller;

import com.MediCareDrone.model.ProductModel;
import com.MediCareDrone.util.CookieUtil;
import com.MediCareDrone.DAO.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 23048591 Suman Lama
 * ProductDetails servlet - handles access to the productDetails.jsp page
 * Manages user authentication and CRUD operations for products
 */
@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
    private ProductDAO productDAO;

    /**
     * Initializes the servlet by creating a ProductDAO instance
     */
    @Override
    public void init() {
        // STEP 1: Initialize ProductDAO for database operations
        productDAO = new ProductDAO();
    }

    /**
     * Handles GET requests to display the product list
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

        // STEP 6: Fetch all products from the database
        List<ProductModel> products = productDAO.getAllProducts();
        System.out.println("Retrieved product list, size: " + products.size());

        // STEP 7: Pass data to JSP
        request.setAttribute("products", products);
        request.setAttribute("username", username);

        // STEP 8: Forward to productDetails JSP
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for adding, updating, or deleting products
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        // STEP 6: Retrieve action and drone ID from form
        String action = request.getParameter("action");
        String droneIdStr = request.getParameter("droneId");
        int droneId = 0;

        // STEP 7: Parse drone ID if provided
        if (droneIdStr != null && !droneIdStr.isEmpty()) {
            try {
                droneId = Integer.parseInt(droneIdStr);
            } catch (NumberFormatException e) {
                // STEP 8: Handle invalid drone ID format
                e.printStackTrace();
                System.out.println("Invalid drone ID format: " + droneIdStr);
            }
        }

        // STEP 9: Process the requested action
        if ("add".equals(action)) {
            // STEP 10: Handle product addition
            String droneName = request.getParameter("droneName");
            String payloadCapacity = request.getParameter("payloadCapacity");
            String maxRangeKmStr = request.getParameter("maxRangeKm");
            String priceStr = request.getParameter("price");
            String warrantyPeriod = request.getParameter("warrantyPeriod");

            if (droneName != null && !droneName.isEmpty() && !maxRangeKmStr.isEmpty() && !priceStr.isEmpty()) {
                try {
                    int maxRangeKm = Integer.parseInt(maxRangeKmStr);
                    BigDecimal price = new BigDecimal(priceStr);
                    ProductModel product = new ProductModel(droneName, payloadCapacity, maxRangeKm, price, warrantyPeriod);
                    productDAO.addProduct(product);
                    System.out.println("Added product: " + droneName);
                } catch (NumberFormatException e) {
                    // STEP 11: Handle invalid number format for maxRangeKm or price
                    e.printStackTrace();
                    System.out.println("Invalid number format for maxRangeKm or price");
                }
            }
        } else if ("update".equals(action)) {
            // STEP 12: Handle product update
            String droneName = request.getParameter("droneName");
            String payloadCapacity = request.getParameter("payloadCapacity");
            String maxRangeKmStr = request.getParameter("maxRangeKm");
            String priceStr = request.getParameter("price");
            String warrantyPeriod = request.getParameter("warrantyPeriod");

            if (droneId != 0 && droneName != null && !droneName.isEmpty() && !maxRangeKmStr.isEmpty() && !priceStr.isEmpty()) {
                try {
                    int maxRangeKm = Integer.parseInt(maxRangeKmStr);
                    BigDecimal price = new BigDecimal(priceStr);
                    ProductModel product = new ProductModel(droneId, droneName, payloadCapacity, maxRangeKm, price, warrantyPeriod);
                    productDAO.updateProduct(product);
                    System.out.println("Updated product ID: " + droneId);
                } catch (NumberFormatException e) {
                    // STEP 13: Handle invalid number format for maxRangeKm or price
                    e.printStackTrace();
                    System.out.println("Invalid number format for maxRangeKm or price during update");
                }
            }
        } else if ("delete".equals(action) && droneId != 0) {
            // STEP 14: Handle product deletion
            productDAO.deleteProduct(droneId);
            System.out.println("Deleted product ID: " + droneId);
        }

        // STEP 15: Redirect to ProductDetails page after action
        response.sendRedirect(request.getContextPath() + "/ProductDetails");
    }
}