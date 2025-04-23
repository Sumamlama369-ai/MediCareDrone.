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

@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO(); // Initialize productDAO for database operations
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Session and Cookie Validation for Authentication
        HttpSession session = request.getSession(false);
        String username = null;

        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("✅ Session active for: " + username);
        } else {
            Cookie cookie = CookieUtil.getCookie(request, "username");
            if (cookie != null) {
                username = cookie.getValue();
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("🔁 Session restored from cookie: " + username);
            }
        }

        if (username == null) {
            System.out.println("⚠️ No session or cookie found. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Authenticated – fetch and display product list
        List<ProductModel> products = productDAO.getAllProducts();
        request.setAttribute("products", products);
        request.setAttribute("username", username); // Pass the username to the JSP
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Session and Cookie Validation for Authentication
        HttpSession session = request.getSession(false);
        String username = null;

        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("✅ Session active for: " + username);
        } else {
            Cookie cookie = CookieUtil.getCookie(request, "username");
            if (cookie != null) {
                username = cookie.getValue();
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("🔁 Session restored from cookie: " + username);
            }
        }

        if (username == null) {
            System.out.println("⚠️ No session or cookie found. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Perform actions based on the button clicked (add, update, delete)
        String action = request.getParameter("action");
        String droneIdStr = request.getParameter("droneId");
        int droneId = 0;

        // Check if droneId is provided for delete or update
        if (droneIdStr != null && !droneIdStr.isEmpty()) {
            try {
                droneId = Integer.parseInt(droneIdStr);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle invalid number format error
            }
        }

        if ("add".equals(action)) {
            // Add product logic
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
                    productDAO.addProduct(product); // Add product to the database
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Handle invalid number format for maxRangeKm or price
                }
            }
        } else if ("update".equals(action)) {
            // Update product logic
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
                    productDAO.updateProduct(product); // Update product in the database
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Handle number format exception for maxRangeKm or price
                }
            }
        } else if ("delete".equals(action) && droneId != 0) {
            // Delete product logic
            productDAO.deleteProduct(droneId); // Delete product from the database
        }

        // Redirect back to product details page after action
        response.sendRedirect(request.getContextPath() + "/ProductDetails");
    }
}
