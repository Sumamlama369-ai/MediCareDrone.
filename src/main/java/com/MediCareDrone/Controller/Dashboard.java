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
 * 23048591SumanLama
 * Dashboard servlet - handles access to the dashboard.jsp page
 * This version includes login authentication using session and cookie
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to /Dashboard
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 🔐 STEP 1: Try to retrieve an existing session (do not create new)
		HttpSession session = request.getSession(false);
		String username = null; // This will store the logged-in username

		// ✅ STEP 2: Check if session exists and contains a valid username
		if (session != null && session.getAttribute("username") != null) {
			username = (String) session.getAttribute("username");
			System.out.println("✅ User authenticated via session: " + username);
		} else {
			// 🟨 STEP 3: If no session, check for the "username" cookie
			Cookie usernameCookie = CookieUtil.getCookie(request, "username");
			if (usernameCookie != null) {
				username = usernameCookie.getValue();

				// ♻️ STEP 4: Recreate session if cookie found
				session = request.getSession(); // create new session
				session.setAttribute("username", username);
				System.out.println("🔁 Session restored from cookie: " + username);
			}
		}

		// ❌ STEP 5: If no session and no cookie, block access and redirect to login
		if (username == null) {
			System.out.println("⛔ Unauthorized access to /Dashboard. Redirecting to login.");
			response.sendRedirect(request.getContextPath() + "/login");
			return; // Stop further execution
		}

		// ✅ STEP 6: User is authenticated, pass data to the dashboard JSP
		request.setAttribute("username", username); // Optional: use it in JSP if needed
		request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to /Dashboard (same as GET)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Reuse GET method logic
		doGet(request, response);
	}
}
