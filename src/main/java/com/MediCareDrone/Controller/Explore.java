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
 * 23048591SumanLama
 * Explore servlet - handles the explore page
 * Now enhanced to include session, cookie authentication, and drone search
 */
@WebServlet("/Explore")
public class Explore extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO(); // Initialize the DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 🔐 Session or Cookie-based Authentication
        HttpSession session = request.getSession(false);
        String username = null;

        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("✅ User authenticated via session: " + username);
        } else {
            Cookie usernameCookie = CookieUtil.getCookie(request, "username");
            if (usernameCookie != null) {
                username = usernameCookie.getValue();
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("🔁 Session restored from cookie: " + username);
            }
        }

        if (username == null) {
            System.out.println("⛔ Unauthorized access to /Explore. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Handle Search Query from Search Bar
        String searchQuery = request.getParameter("query");
        List<ProductModel> products;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            products = productDAO.searchProductsByName(searchQuery.trim());
            System.out.println("🔍 Search query: " + searchQuery);
        } else {
            products = productDAO.getAllProducts();
            System.out.println("📦 Showing all products");
        }

        // ✅ Pass data to JSP
        request.setAttribute("username", username);
        request.setAttribute("products", products);
        request.setAttribute("searchQuery", searchQuery); // Optional: to retain the value in the input box

        request.getRequestDispatcher("/WEB-INF/pages/explore.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Allow POST to behave the same way as GET
    }
}
