
package com.MediCareDrone.DAO;

import com.MediCareDrone.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

   
    public int getTotalOrderCount() {
        int count = 0;
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Order_Info");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
 // Inside OrderDAO.java
    public double getAverageOrderValue() {
        double avgOrderValue = 0;

        String query = "SELECT AVG(Total_Price) FROM Order_Info";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                avgOrderValue = rs.getDouble(1);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return avgOrderValue;
    }


}
