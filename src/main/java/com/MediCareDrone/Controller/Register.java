package com.MediCareDrone.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.MediCareDrone.model.UserModel;
import com.MediCareDrone.util.ValidationUtil;
import com.MediCareDrone.Controller.Hashing.*;
import com.MediCareDrone.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Validate the registration form
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                handleError(req, resp, validationMessage);
                return;
            }

            // Extract user model
            UserModel userModel = extractUserModel(req);

            // Hash the password before storing it in the database
            String hashedPassword = Hashing.hashPassword(userModel.getPassword());
            userModel.setPassword(hashedPassword);

            // Save to the database
            boolean isAdded = saveUserToDB(userModel);

            if (isAdded) {
                handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
            } else {
                handleError(req, resp, "Could not register your account. Please try again later!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "An unexpected error occurred. Please try again later!");
        }
    }

    private UserModel extractUserModel(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        return new UserModel(firstName, lastName, username, gender, email, phone, password);
    }

    private String validateRegistrationForm(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypePassword");

        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required.";
        if (!ValidationUtil.isAlphabetic(firstName)) return "First name must contain only letters.";
        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required.";
        if (!ValidationUtil.isAlphabetic(lastName)) return "Last name must contain only letters.";
        if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(gender)) return "Gender is required.";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
        if (!ValidationUtil.isValidEmail(email)) return "Please enter a valid email address.";
        if (ValidationUtil.isNullOrEmpty(phone)) return "Phone number is required.";
        if (!ValidationUtil.isValidPhoneNumber(phone)) return "Phone number must be 10 digits starting with 98.";
        if (ValidationUtil.isNullOrEmpty(password)) return "Password is required.";
        if (ValidationUtil.isNullOrEmpty(retypePassword)) return "Please retype the password.";
        if (!ValidationUtil.doPasswordsMatch(password, retypePassword)) return "Passwords do not match.";

        return null;
    }

    private boolean saveUserToDB(UserModel userModel) {
        try (Connection conn = DbConfig.getDbConnection()) {
            String query = "INSERT INTO User_Details (First_Name, Last_Name, Username, Gender, Email, Phone, Password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, userModel.getFirstName());
                stmt.setString(2, userModel.getLastName());
                stmt.setString(3, userModel.getUsername());
                stmt.setString(4, userModel.getGender());
                stmt.setString(5, userModel.getEmail());
                stmt.setString(6, userModel.getPhone());
                stmt.setString(7, userModel.getPassword());

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.setAttribute("firstName", req.getParameter("firstName"));
        req.setAttribute("lastName", req.getParameter("lastName"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("gender", req.getParameter("gender"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("phone", req.getParameter("phone"));
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}