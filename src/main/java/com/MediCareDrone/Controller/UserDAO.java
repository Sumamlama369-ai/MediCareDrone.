package com.MediCareDrone.Controller;

import com.MediCareDrone.config.DbConfig;
import com.MediCareDrone.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        try (Connection conn = DbConfig.getDbConnection()) {
            String sql = "SELECT * FROM User_Details";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UserModel user = new UserModel(
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Username"),
                    rs.getString("Gender"),
                    rs.getString("Password")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(UserModel user) {
    	// Check if the username or email already exists
        if (isUserExists(user.getUsername(), user.getEmail())) {
            System.out.println("Username or Email already exists.");
            return false;
        }
    	
    	
        String sql = "INSERT INTO User_Details (First_Name, Last_Name, Email, Phone, Username, Gender, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getUsername());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM User_Details WHERE Username=?";
        try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(UserModel user) {
    	// Check if the username or email exists
        if (!isUserExists(user.getUsername(), user.getEmail())) {
            System.out.println("User does not exist.");
            return false;
        }
    	
        String sql = "UPDATE User_Details SET First_Name=?, Last_Name=?, Email=?, Phone=?, Gender=?, Password=? WHERE Username=?";
        try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getUsername());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    public boolean isUserExists(String username, String email) {
        String sql = "SELECT 1 FROM User_Details WHERE Username = ? OR Email = ?";
        try (Connection conn = DbConfig.getDbConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Returns true if a matching user is found
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM User_Details WHERE Username = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
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
            e.printStackTrace();
        }
        return null;
    }


    public int getTotalUserCount() {
        int count = 0;
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM User_Details");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


 


}
