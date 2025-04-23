package com.MediCareDrone.Controller;

import com.MediCareDrone.DAO.UserDAO;
import com.MediCareDrone.model.UserModel;
import com.MediCareDrone.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Session and Cookie Validation
        HttpSession session = request.getSession(false);
        String username = null;

        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
            System.out.println("✅ Session active for: " + username);
        } else {
            Cookie cookie = CookieUtil.getCookie(request, "username");
            if (cookie != null) {
                username = cookie.getValue();
                session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("🔁 Session restored from cookie: " + username);
            }
        }

        if (username == null) {
            System.out.println("⚠️ No session or cookie found. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Authenticated – fetch and display user list
        List<UserModel> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.setAttribute("username", username); // pass to JSP
        request.getRequestDispatcher("/WEB-INF/pages/userDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        UserModel user = new UserModel(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("phone"),
                request.getParameter("username"),
                request.getParameter("gender"),
                request.getParameter("password")
        );

        switch (action) {
            case "add":
                userDAO.addUser(user);
                break;
            case "update":
                userDAO.updateUser(user);
                break;
            case "delete":
                userDAO.deleteUser(user.getUsername());
                break;
        }

        response.sendRedirect(request.getContextPath() + "/UserDetails"); // ✅ correct
    }
}
