package com.MediCareDrone.Controller;

import com.MediCareDrone.util.CookieUtil;
import com.MediCareDrone.DAO.UserDAO; // 
import com.MediCareDrone.DAO.OrderDAO; // 
import com.MediCareDrone.DAO.ProductDAO;
import com.MediCareDrone.config.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 23048591SumanLama
 * Dashboard servlet - handles access to the dashboard.jsp page
 * This version includes login authentication using session and cookie
 */
@WebServlet("/DashboardUser")
public class DashboardUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			System.out.println("⛔ Unauthorized access to /Dashboard. Redirecting to login.");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// ✅ Get total registered user count
		UserDAO userDAO = new UserDAO();
		int totalUsers = userDAO.getTotalUserCount();
		request.setAttribute("totalUsers", totalUsers); // send to JSP
		System.out.println("Total users from DAO: " + totalUsers);

		
		
		
		int totalDrones = new ProductDAO().getTotalDroneCount();
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalDrones", totalDrones);

		
		int totalOrders = new OrderDAO().getTotalOrderCount();
		request.setAttribute("totalOrders", totalOrders);

		
		double avgOrderValue = new OrderDAO().getAverageOrderValue();
		request.setAttribute("avgOrderValue", avgOrderValue);

		
		
		
		
		
		
		request.setAttribute("username", username);
		request.getRequestDispatcher("/WEB-INF/pages/dashboardUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
