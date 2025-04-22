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
 * Explore servlet - handles the explore page
 * Now enhanced to include session and cookie-based authentication
 */
@WebServlet("/Explore")
public class Explore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET request to /Explore
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 🔐 STEP 1: Try to get existing session (false = don't create a new session)
		HttpSession session = request.getSession(false);
		String username = null; // Placeholder for authenticated username

		// ✅ STEP 2: Check if session exists and contains "username"
		if (session != null && session.getAttribute("username") != null) {
			username = (String) session.getAttribute("username");
			System.out.println("✅ User authenticated via session: " + username);
		} else {
			// 🔁 STEP 3: If no session, try checking cookie
			Cookie usernameCookie = CookieUtil.getCookie(request, "username");
			if (usernameCookie != null) {
				username = usernameCookie.getValue();

				// ♻️ Recreate session from cookie if valid username is found
				session = request.getSession(); // create a new session
				session.setAttribute("username", username);
				System.out.println("🔁 Session restored from cookie: " + username);
			}
		}

		// ❌ STEP 4: If neither session nor cookie provided a username, block access
		if (username == null) {
			System.out.println("⛔ Unauthorized access to /Explore. Redirecting to login.");
			response.sendRedirect(request.getContextPath() + "/login"); // Send user back to login
			return;
		}

		// ✅ STEP 5: Authenticated user, forward to the Explore JSP page
		request.setAttribute("username", username); // Pass username to the JSP if needed
		request.getRequestDispatcher("/WEB-INF/pages/explore.jsp").forward(request, response);
	}

	/**
	 * Handles POST request to /Explore (same as GET)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 🔁 Reuse doGet logic for POST requests
		doGet(request, response);
	}
}
