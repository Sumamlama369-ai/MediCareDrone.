package com.MediCareDrone.DAO;

import com.MediCareDrone.config.DbConfig;
import com.MediCareDrone.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for handling user-related database operations.
 * This class provides methods to perform CRUD (Create, Read, Update, Delete) operations
 * on the User_Details table in the database for user management in the MediCareDrone system.
 */
public class UserDAO {

    /**
     * Retrieves all users from the database.
     * 
     * @return A List of UserModel objects containing all users in the database.
     *         Returns an empty list if no users are found or if an error occurs.
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();  // Initialize empty list to store users
        try (Connection conn = DbConfig.getDbConnection()) {  // Establish database connection
            String sql = "SELECT * FROM User_Details";  // SQL query to get all users
            PreparedStatement stmt = conn.prepareStatement(sql);  // Prepare SQL statement
            ResultSet rs = stmt.executeQuery();  // Execute query and get results

            while (rs.next()) {  // Iterate through each row in the result set
                // Create a new UserModel object for each row
                UserModel user = new UserModel(
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Username"),
                    rs.getString("Gender"),
                    rs.getString("Password")
                );
                users.add(user);  // Add user to the list
            }
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return users;  // Return list of users (may be empty if error occurred)
    }

    /**
     * Adds a new user to the database after checking if the username or email already exists.
     * 
     * @param user The UserModel object containing the user information to be added.
     * @return true if the user was successfully added, false if the user already exists or if an error occurs.
     */
    public boolean addUser(UserModel user) {
    	// Check if the username or email already exists
        if (isUserExists(user.getUsername(), user.getEmail())) {
            System.out.println("Username or Email already exists.");
            return false;  // Return false if user already exists
        }
    	
    	
        String sql = "INSERT INTO User_Details (First_Name, Last_Name, Email, Phone, Username, Gender, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameters
            
            // Set values for each parameter in the prepared statement
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getUsername());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPassword());
            
            return stmt.executeUpdate() > 0;  // Return true if at least one row was affected (inserted)
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed
    }

    /**
     * Deletes a user from the database by username.
     * 
     * @param username The username of the user to be deleted.
     * @return true if the user was successfully deleted, false otherwise.
     */
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM User_Details WHERE Username=?";  // SQL query to delete user by username
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameter
            
            stmt.setString(1, username);  // Set the username parameter
            return stmt.executeUpdate() > 0;  // Return true if at least one row was affected (deleted)
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed
    }

    /**
     * Updates an existing user in the database after verifying the user exists.
     * 
     * @param user The UserModel object containing the updated user information.
     *             The username in the model is used to identify which user to update.
     * @return true if the user was successfully updated, false if the user doesn't exist or if an error occurs.
     */
    public boolean updateUser(UserModel user) {
    	// Check if the username or email exists
        if (!isUserExists(user.getUsername(), user.getEmail())) {
            System.out.println("User does not exist.");
            return false;  // Return false if user doesn't exist
        }
    	
        String sql = "UPDATE User_Details SET First_Name=?, Last_Name=?, Email=?, Phone=?, Gender=?, Password=? WHERE Username=?";
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameters
            
            // Set values for each parameter in the prepared statement
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getUsername());  // The WHERE condition to identify the user to update
            
            return stmt.executeUpdate() > 0;  // Return true if at least one row was affected (updated)
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed
    }
    
    
    /**
     * Checks if a user with the given username or email already exists in the database.
     * 
     * @param username The username to check for existence.
     * @param email The email to check for existence.
     * @return true if a user with the given username or email exists, false otherwise.
     */
    public boolean isUserExists(String username, String email) {
        String sql = "SELECT 1 FROM User_Details WHERE Username = ? OR Email = ?";  // SQL query to check existence
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameters
            
            stmt.setString(1, username);  // Set username parameter
            stmt.setString(2, email);     // Set email parameter
            
            ResultSet rs = stmt.executeQuery();  // Execute query and get results
            return rs.next();  // Returns true if a matching user is found
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed or no match found
    }
    
    /**
     * Retrieves a user from the database by username.
     * 
     * @param username The username of the user to retrieve.
     * @return A UserModel object if the user is found, null otherwise.
     */
    public UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM User_Details WHERE Username = ?";  // SQL query to get user by username
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameter
            
            stmt.setString(1, username);  // Set the username parameter
            ResultSet rs = stmt.executeQuery();  // Execute query and get results
            
            if (rs.next()) {  // If a matching user is found
                // Create and return a new UserModel with the user's data
                return new UserModel(
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Username"),
                    rs.getString("Gender"),
                    rs.getString("Password")
                );
            }
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return null;  // Return null if no user found or if an error occurs
    }


    /**
     * Counts the total number of users in the database.
     * 
     * @return The total count of users in the User_Details table.
     *         Returns 0 if there are no users or if an error occurs.
     */
    public int getTotalUserCount() {
        int count = 0;
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM User_Details");  // Prepare SQL statement
             ResultSet rs = stmt.executeQuery()) {  // Execute query and get results
            
            if (rs.next()) {  // Move to first row of results
                count = rs.getInt(1);  // Get the count value from first column
            }
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return count;  // Return the count (0 if operation failed)
    }


}