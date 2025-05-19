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

/**
 * Authentication and Authorization Filter for the MediCareDrone application.
 * This filter intercepts all incoming requests to enforce access control based on user authentication status and role.
 * It ensures that:
 * - Unauthenticated users can only access public resources and authentication-related pages
 * - Authenticated users are redirected from login pages to appropriate dashboards
 * - Access to role-specific areas is restricted (admin vs regular user)
 * - Static resources are always accessible
 */
@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {
    
    /**
     * URI constant for the login page
     */
    private static final String LOGIN = "/login";
    
    /**
     * URI constant for the registration page
     */
    private static final String REGISTER = "/register";
    
    /**
     * URI constant for the home page
     */
    private static final String HOME = "/home";
    
    /**
     * URI constant for the root/index page
     */
    private static final String ROOT = "/";
    
    /**
     * URI constant for the admin dashboard
     */
    private static final String ADMIN_DASHBOARD = "/Dashboard";
    
    /**
     * URI constant for the regular user dashboard
     */
    private static final String USER_DASHBOARD = "/DashboardUser";

    /**
     * Initializes the filter.
     * This method is called by the web container when the filter is instantiated.
     * 
     * @param filterConfig Configuration object for this filter
     * @throws ServletException If an error occurs during initialization
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if required
    }

    /**
     * Main filtering method that implements the access control logic.
     * This method is called by the container each time a request/response pair is passed through the chain.
     * 
     * @param request The ServletRequest object contains the client's request
     * @param response The ServletResponse object contains the filter's response
     * @param chain The FilterChain for invoking the next filter or the resource
     * @throws IOException If an I/O error occurs during this filter's processing
     * @throws ServletException If the processing fails for any other reason
     */
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
            chain.doFilter(request, response);  // Pass the request along the filter chain
            return;
        }
        
        // Check if user is logged in (checking session for 'username')
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
        
        if (!isLoggedIn) {
            // User not logged in, allow access to login and register pages
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                chain.doFilter(request, response);  // Allow access to authentication pages
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN);  // Redirect to login if not logged in
            }
        } else {
            // User is logged in, check the role
            String username = (String) SessionUtil.getAttribute(req, "username");
            
            if ("admin".equals(username)) {
                // If username is 'admin', allow access to admin dashboard and restrict access to user dashboard
                if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                    res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);  // Redirect authenticated admin from login pages
                } else if (uri.endsWith(USER_DASHBOARD)) {
                    res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);  // Redirect admin users to admin dashboard
                } else {
                    chain.doFilter(request, response);  // Allow admin access to other pages
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

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service.
     * This method is called when the filter instance is being destroyed.
     */
    @Override
    public void destroy() {
        // Cleanup logic, if required
    }
}