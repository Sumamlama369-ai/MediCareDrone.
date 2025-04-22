package com.MediCareDrone.Controller;

import com.MediCareDrone.model.UserModel;
import com.MediCareDrone.Controller.UserDAO;
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
 * Servlet implementation for the Portfolio page.
 * Handles requests for displaying the user's portfolio.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Portfolio" })
public class Portfolio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to /Portfolio
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			System.out.println("⛔ Unauthorized access to /Portfolio. Redirecting to login.");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			// ✅ Get user details using DAO
			UserDAO userDAO = new UserDAO();
			UserModel user = userDAO.getUserByUsername(username); // Make sure this method exists

			if (user != null) {
				// Pass the entire user object to JSP
				request.setAttribute("user", user);
			} else {
				System.out.println("⚠️ No user found with username: " + username);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ Error retrieving user details: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
