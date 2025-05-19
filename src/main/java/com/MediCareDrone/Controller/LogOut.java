package com.MediCareDrone.Controller;

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
 * LogOut servlet - handles user logout functionality
 * Invalidates the session, removes relevant cookies, and redirects the user
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogOut extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests for logout (e.g., direct URL navigation)
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // STEP 1: Delegate to common logout processing method
        processLogout(request, response);
    }

    /**
     * Handles POST requests for logout (e.g., clicking a logout button)
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // STEP 1: Delegate to common logout processing method
        processLogout(request, response);
    }

    /**
     * Processes logout logic: invalidates session, removes cookies, and redirects
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // STEP 1: Retrieve the current session, if it exists
        HttpSession session = request.getSession(false);

        // STEP 2: Invalidate the session to clear user data
        if (session != null) {
            session.invalidate();
            System.out.println("Successfully invalidated the active session");
        }

        // STEP 3: Remove login-related cookies
        CookieUtil.deleteCookie(response, "username"); // Custom username cookie
        CookieUtil.deleteCookie(response, "JSESSIONID"); // Servlet container session cookie
        System.out.println("Successfully removed login-related cookies");

        // STEP 4: Redirect to the main landing page
        response.sendRedirect(request.getContextPath() + "/firstPage.jsp");
        System.out.println("Redirected to firstPage.jsp after logout");
    }
}