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
 * Handles requests for displaying the user's portfolio and updating basic info.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Portfolio" })
public class Portfolio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String username = null;

		if (session != null && session.getAttribute("username") != null) {
			username = (String) session.getAttribute("username");
			System.out.println("User authenticated via session: " + username);
		} else {
			Cookie usernameCookie = CookieUtil.getCookie(request, "username");
			if (usernameCookie != null) {
				username = usernameCookie.getValue();
				session = request.getSession();
				session.setAttribute("username", username);
				System.out.println("Session restored from cookie: " + username);
			}
		}

		if (username == null) {
			System.out.println("Unauthorized access to /Portfolio. Redirecting to login.");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			UserDAO userDAO = new UserDAO();
			UserModel user = userDAO.getUserByUsername(username);

			if (user != null) {
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

	/**
	 * Handles POST requests to /Portfolio for updating first and last name.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String updatedFirstName = request.getParameter("firstName");
		String updatedLastName = request.getParameter("lastName");

		UserDAO userDAO = new UserDAO();
		UserModel user = userDAO.getUserByUsername(username);

		if (user != null) {
			user.setFirstName(updatedFirstName);
			user.setLastName(updatedLastName);

			boolean updated = userDAO.updateUser(user);

			if (updated) {
				System.out.println("User profile updated successfully for: " + username);
				request.setAttribute("successMessage", "Profile updated successfully.");
			} else {
				System.out.println("Failed to update user profile for: " + username);
				request.setAttribute("errorMessage", "Failed to update profile.");
			}
		} else {
			System.out.println("No user found to update with username: " + username);
			request.setAttribute("errorMessage", "User not found.");
		}

		// Reuse the doGet method to reload updated profile info
		doGet(request, response);
	}
}
