package com.MediCareDrone.filter;

import java.io.IOException;
import com.MediCareDrone.util.SessionUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/";
    private static final String ADMIN_DASHBOARD = "/Dashboard";
    private static final String USER_DASHBOARD = "/DashboardUser";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast the request and response to HttpServletRequest and HttpServletResponse
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Get the requested URI
        String uri = req.getRequestURI();

        // Allow access to static resources like images and stylesheets
        if (uri.endsWith(".css") || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(HOME) || uri.endsWith(ROOT)) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in (checking session for 'username')
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

        if (!isLoggedIn) {
            // User not logged in, allow access to login and register pages
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN);  // Redirect to login if not logged in
            }
        } else {
            // User is logged in, check the role
            String username = (String) SessionUtil.getAttribute(req, "username");
            if ("admin".equals(username)) {
                // If username is 'admin', allow access to admin dashboard and restrict access to user dashboard
                if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                    res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);
                } else if (uri.endsWith(USER_DASHBOARD)) {
                    res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);  // Redirect admin users to admin dashboard
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                // If the username is not 'admin', assume it's a regular user and allow access to user dashboard
                if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                    res.sendRedirect(req.getContextPath() + HOME);  // Redirect to home if already logged in
                } else if (uri.endsWith(ADMIN_DASHBOARD)) {
                    res.sendRedirect(req.getContextPath() + HOME);  // Prevent access to admin dashboard
                } else {
                    chain.doFilter(request, response);  // Allow access to regular user pages
                }
            }
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic, if required
    }
}
