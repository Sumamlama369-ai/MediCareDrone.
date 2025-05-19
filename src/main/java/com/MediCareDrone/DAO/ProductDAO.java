package com.MediCareDrone.DAO;

import com.MediCareDrone.config.DbConfig;
import com.MediCareDrone.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for handling product-related database operations.
 * This class provides methods to perform CRUD (Create, Read, Update, Delete) operations
 * on the Product_Details table in the database for managing drone products.
 */
public class ProductDAO {

    /**
     * Retrieves all products from the database.
     * 
     * @return A List of ProductModel objects containing all products in the database.
     *         Returns an empty list if no products are found or if an error occurs.
     */
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();  // Initialize empty list to store products
        String sql = "SELECT * FROM Product_Details";     // SQL query to get all products

        try (Connection conn = DbConfig.getDbConnection(); // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql); // Prepare SQL statement
             ResultSet rs = stmt.executeQuery()) {  // Execute query and get results

            while (rs.next()) {  // Iterate through each row in the result set
                // Create a new ProductModel object for each row
                ProductModel product = new ProductModel(
                    rs.getInt("Drone_ID"),
                    rs.getString("Drone_Name"),
                    rs.getString("Payload_Capacity"),
                    rs.getInt("Max_Range_KM"),
                    rs.getBigDecimal("Price"),
                    rs.getString("Warranty_Period")
                );
                products.add(product);  // Add product to the list
            }

        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return products;  // Return list of products (may be empty if error occurred)
    }

    /**
     * Adds a new product to the database.
     * 
     * @param product The ProductModel object containing the product information to be added.
     * @return true if the product was successfully added, false otherwise.
     */
    public boolean addProduct(ProductModel product) {
        String sql = "INSERT INTO Product_Details (Drone_Name, Payload_Capacity, Max_Range_KM, Price, Warranty_Period) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameters

            // Set values for each parameter in the prepared statement
            stmt.setString(1, product.getDroneName());
            stmt.setString(2, product.getPayloadCapacity());
            stmt.setInt(3, product.getMaxRangeKm());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setString(5, product.getWarrantyPeriod());

            return stmt.executeUpdate() > 0;  // Return true if at least one row was affected (inserted)

        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed
    }

    /**
     * Deletes a product from the database by its ID.
     * 
     * @param droneId The ID of the drone product to be deleted.
     * @return true if the product was successfully deleted, false otherwise.
     */
    public boolean deleteProduct(int droneId) {
        String sql = "DELETE FROM Product_Details WHERE Drone_ID=?";  // SQL query to delete product by ID

        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameter

            stmt.setInt(1, droneId);  // Set the drone ID parameter
            return stmt.executeUpdate() > 0;  // Return true if at least one row was affected (deleted)

        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed
    }

    /**
     * Updates an existing product in the database.
     * 
     * @param product The ProductModel object containing the updated product information.
     *                The drone ID in the model is used to identify which product to update.
     * @return true if the product was successfully updated, false otherwise.
     */
    public boolean updateProduct(ProductModel product) {
        String sql = "UPDATE Product_Details SET Drone_Name=?, Payload_Capacity=?, Max_Range_KM=?, Price=?, Warranty_Period=? WHERE Drone_ID=?";

        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameters

            // Set values for each parameter in the prepared statement
            stmt.setString(1, product.getDroneName());
            stmt.setString(2, product.getPayloadCapacity());
            stmt.setInt(3, product.getMaxRangeKm());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setString(5, product.getWarrantyPeriod());
            stmt.setInt(6, product.getDroneId());  // The WHERE condition to identify the product to update

            return stmt.executeUpdate() > 0;  // Return true if at least one row was affected (updated)

        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return false;  // Return false if operation failed
    }
    
    /**
     * Counts the total number of products in the database.
     * 
     * @return The total count of products in the Product_Details table.
     *         Returns 0 if there are no products or if an error occurs.
     */
    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM Product_Details";  // SQL query to count all products
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql);  // Prepare SQL statement
             ResultSet rs = stmt.executeQuery()) {  // Execute query and get results
            if (rs.next()) {  // Move to first row of results
                return rs.getInt(1);  // Get the count value from first column
            }
        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }
        return 0;  // Return 0 if operation failed or no products found
    }
    
    /**
     * Counts the total number of drones in the database.
     * This method serves the same purpose as getTotalProducts() but with a different name.
     * 
     * @return The total count of drones in the Product_Details table.
     *         Returns 0 if there are no drones or if an error occurs.
     */
    public int getTotalDroneCount() {
        int count = 0;
        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Product_Details");  // Prepare SQL statement
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

    /**
     * Searches for products by name using partial matching.
     * 
     * @param searchQuery The search string to match against drone names.
     * @return A List of ProductModel objects that match the search criteria.
     *         Returns an empty list if no matching products are found or if an error occurs.
     */
    public List<ProductModel> searchProductsByName(String searchQuery) {
        List<ProductModel> products = new ArrayList<>();  // Initialize empty list to store matching products
        String sql = "SELECT * FROM Product_Details WHERE Drone_Name LIKE ?";  // SQL query for name search with wildcard

        try (Connection conn = DbConfig.getDbConnection();  // Establish database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Prepare SQL statement with parameter

            stmt.setString(1, "%" + searchQuery + "%");  // Set search parameter with wildcards for partial matching

            try (ResultSet rs = stmt.executeQuery()) {  // Execute query and get results
                while (rs.next()) {  // Iterate through each row in the result set
                    // Create a new ProductModel object for each matching row
                    ProductModel product = new ProductModel(
                        rs.getInt("Drone_ID"),
                        rs.getString("Drone_Name"),
                        rs.getString("Payload_Capacity"),
                        rs.getInt("Max_Range_KM"),
                        rs.getBigDecimal("Price"),
                        rs.getString("Warranty_Period")
                    );
                    products.add(product);  // Add product to the list
                }
            }

        } catch (Exception e) {
            // Print stack trace if any exception occurs during database operation
            e.printStackTrace();
        }

        return products;  // Return list of matching products (may be empty if none found or error occurred)
    }

}