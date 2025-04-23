package com.MediCareDrone.DAO;

import com.MediCareDrone.config.DbConfig;
import com.MediCareDrone.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ✅ Get all products from the database
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM Product_Details";

        try (Connection conn = DbConfig.getDbConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("Drone_ID"),
                    rs.getString("Drone_Name"),
                    rs.getString("Payload_Capacity"),
                    rs.getInt("Max_Range_KM"),
                    rs.getBigDecimal("Price"),
                    rs.getString("Warranty_Period")
                );
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // ✅ Add a new product
    public boolean addProduct(ProductModel product) {
        String sql = "INSERT INTO Product_Details (Drone_Name, Payload_Capacity, Max_Range_KM, Price, Warranty_Period) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getDroneName());
            stmt.setString(2, product.getPayloadCapacity());
            stmt.setInt(3, product.getMaxRangeKm());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setString(5, product.getWarrantyPeriod());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Delete a product by ID
    public boolean deleteProduct(int droneId) {
        String sql = "DELETE FROM Product_Details WHERE Drone_ID=?";

        try (Connection conn = DbConfig.getDbConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, droneId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Update an existing product
    public boolean updateProduct(ProductModel product) {
        String sql = "UPDATE Product_Details SET Drone_Name=?, Payload_Capacity=?, Max_Range_KM=?, Price=?, Warranty_Period=? WHERE Drone_ID=?";

        try (Connection conn = DbConfig.getDbConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getDroneName());
            stmt.setString(2, product.getPayloadCapacity());
            stmt.setInt(3, product.getMaxRangeKm());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setString(5, product.getWarrantyPeriod());
            stmt.setInt(6, product.getDroneId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM Product_Details";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalDroneCount() {
        int count = 0;
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Product_Details");
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
