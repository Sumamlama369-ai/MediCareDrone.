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
 * LogOut servlet handles user logout.
 * It invalidates the session, removes relevant cookies, and redirects the user.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogOut extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET request for logout (in case users navigate via URL).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLogout(request, response);
    }

    /**
     * Handles POST request for logout (when user clicks Logout button).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLogout(request, response);
    }

    /**
     * Common method to handle logout logic: session invalidation, cookie cleanup, and redirection.
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Get the current session (if any), and invalidate it
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroys the session and clears all user data
        }

        // Remove any cookies related to login or user data
        CookieUtil.deleteCookie(response, "username");      // Custom username cookie
        CookieUtil.deleteCookie(response, "JSESSIONID");    // Session cookie managed by servlet container

        // Redirect the user to the main landing page after logout
        response.sendRedirect(request.getContextPath() + "/firstPage.jsp");
    }
}
