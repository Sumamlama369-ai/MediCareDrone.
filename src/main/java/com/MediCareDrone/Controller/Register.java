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

/**
 * @author 23048591 Suman Lama
 * Register servlet - handles user registration
 * Validates form input, hashes passwords, and stores user data in the database
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the registration form
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // STEP 1: Forward the request to the register JSP page
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests to process registration form submissions
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // STEP 1: Validate the registration form
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                // STEP 2: If validation fails, handle error and return
                handleError(req, resp, validationMessage);
                return;
            }

            // STEP 3: Extract user data from the form
            UserModel userModel = extractUserModel(req);

            // STEP 4: Hash the user's password
            String hashedPassword = Hashing.hashPassword(userModel.getPassword());
            userModel.setPassword(hashedPassword);

            // STEP 5: Save user data to the database
            boolean isAdded = saveUserToDB(userModel);

            // STEP 6: Handle registration result
            if (isAdded) {
                // STEP 7: On success, redirect to login page with success message
                handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
            } else {
                // STEP 8: On failure, display error message on register page
                handleError(req, resp, "Could not register your account. Please try again later!");
            }
        } catch (Exception e) {
            // STEP 9: Handle unexpected errors
            e.printStackTrace();
            handleError(req, resp, "An unexpected error occurred. Please try again later!");
        }
    }

    /**
     * Extracts user data from the registration form into a UserModel
     *
     * @param req the HttpServletRequest object
     * @return a UserModel containing the user's registration data
     */
    private UserModel extractUserModel(HttpServletRequest req) {
        // STEP 1: Retrieve form parameters
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        // STEP 2: Create and return UserModel
        return new UserModel(firstName, lastName, username, gender, email, phone, password);
    }

    /**
     * Validates the registration form input
     *
     * @param req the HttpServletRequest object
     * @return null if validation passes, or an error message if validation fails
     */
    private String validateRegistrationForm(HttpServletRequest req) {
        // STEP 1: Retrieve form parameters
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypePassword");

        // STEP 2: Perform validation checks
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

        // STEP 3: Return null if all validations pass
        return null;
    }

    /**
     * Saves the user data to the database
     *
     * @param userModel the UserModel containing user data
     * @return true if the user is successfully saved, false otherwise
     */
    private boolean saveUserToDB(UserModel userModel) {
        try (Connection conn = DbConfig.getDbConnection()) {
            // STEP 1: Prepare SQL query to insert user data
            String query = "INSERT INTO User_Details (First_Name, Last_Name, Username, Gender, Email, Phone, Password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                // STEP 2: Set query parameters
                stmt.setString(1, userModel.getFirstName());
                stmt.setString(2, userModel.getLastName());
                stmt.setString(3, userModel.getUsername());
                stmt.setString(4, userModel.getGender());
                stmt.setString(5, userModel.getEmail());
                stmt.setString(6, userModel.getPhone());
                stmt.setString(7, userModel.getPassword());

                // STEP 3: Execute query and check result
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // STEP 4: Handle database or connection errors
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Handles successful registration by setting a success message and forwarding
     *
     * @param req          the HttpServletRequest object
     * @param resp         the HttpServletResponse object
     * @param message      the success message to display
     * @param redirectPage the JSP page to forward to
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        // STEP 1: Set success message and forward to specified page
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }

    /**
     * Handles registration errors by setting an error message and preserving form data
     *
     * @param req     the HttpServletRequest object
     * @param resp    the HttpServletResponse object
     * @param message the error message to display
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        // STEP 1: Set error message and preserve form data
        req.setAttribute("error", message);
        req.setAttribute("firstName", req.getParameter("firstName"));
        req.setAttribute("lastName", req.getParameter("lastName"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("gender", req.getParameter("gender"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("phone", req.getParameter("phone"));

        // STEP 2: Forward back to register JSP
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}