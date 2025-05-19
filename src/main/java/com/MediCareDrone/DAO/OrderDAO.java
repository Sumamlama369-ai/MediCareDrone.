package com.MediCareDrone.DAO;

import com.MediCareDrone.config.DbConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for handling order-related database operations.
 * This class provides methods to interact with the Order_Info table in the database.
 * It encapsulates SQL queries and database connection handling for order information.
 */
public class OrderDAO {
   
    /**
     * Retrieves the total number of orders from the database.
     * 
     * @return The total count of orders in the Order_Info table.
     *         Returns 0 if there are no orders or if an error occurs.
     */
    public int getTotalOrderCount() {
        int count = 0;
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             // Prepare SQL statement to count all orders
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Order_Info");
             // Execute the query and get results
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {  // Move to first row of results
                count = rs.getInt(1);  // Get the count value from first column
            }
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return count;
    }
    
    /**
     * Calculates the average value of all orders in the database.
     * 
     * @return The average order value from the Order_Info table.
     *         Returns 0.0 if there are no orders or if an error occurs.
     * @throws SQLException If a database access error occurs
     * @throws ClassNotFoundException If the database driver class is not found
     */
    public double getAverageOrderValue() {
        double avgOrderValue = 0;
        String query = "SELECT AVG(Total_Price) FROM Order_Info";  // SQL query to calculate average price
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(query);  // Prepare the SQL statement
             ResultSet rs = stmt.executeQuery()) {  // Execute query and store results
            if (rs.next()) {  // Move to first row of results
                avgOrderValue = rs.getDouble(1);  // Extract average value from first column
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Print stack trace if SQL or ClassNotFound exception occurs
            e.printStackTrace();
        }
        return avgOrderValue;
    }
}