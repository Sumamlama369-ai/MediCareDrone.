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
 * Servlet for handling About Us page access.
 * Now includes session and cookie validation to ensure user authentication.
 */
@WebServlet("/AboutUs")
public class AboutUs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to /AboutUs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 🔐 STEP 1: Attempt to retrieve existing session without creating a new one
		HttpSession session = request.getSession(false);
		String username = null;

		// ✅ STEP 2: Check if user is authenticated via session
		if (session != null && session.getAttribute("username") != null) {
			username = (String) session.getAttribute("username");
			System.out.println("✅ User authenticated via session: " + username);
		} else {
			// 🟨 STEP 3: If no session, check cookie for saved username
			Cookie usernameCookie = CookieUtil.getCookie(request, "username");
			if (usernameCookie != null) {
				username = usernameCookie.getValue();

				// ♻️ STEP 4: Recreate session from cookie if necessary
				session = request.getSession(); // create a new session
				session.setAttribute("username", username);
				System.out.println("🔁 Session restored from cookie: " + username);
			}
		}

		// ❌ STEP 5: If user not authenticated, redirect to login page
		if (username == null) {
			System.out.println("⛔ Unauthorized access to /AboutUs. Redirecting to login.");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// ✅ STEP 6: Authenticated — pass data to the JSP if needed
		request.setAttribute("username", username); // optional for personalization
		request.getRequestDispatcher("/WEB-INF/pages/aboutUs.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to /AboutUs (same as GET)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Reuse the same logic for POST
		doGet(request, response);
	}
}
