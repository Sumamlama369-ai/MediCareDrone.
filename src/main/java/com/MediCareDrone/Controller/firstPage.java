package com.MediCareDrone.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 23048591 Suman Lama
 * Servlet implementation for the first page of the application
 * Handles requests to the root URL and /firstPage, forwarding to firstPage.jsp
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/firstPage", "/" })
public class firstPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /firstPage or root URL
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // STEP 1: Forward the request to the firstPage JSP
        request.getRequestDispatcher("/WEB-INF/pages/firstPage.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /firstPage or root URL (same as GET)
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // STEP 1: Reuse GET method logic for POST requests
        doGet(request, response);
    }
}