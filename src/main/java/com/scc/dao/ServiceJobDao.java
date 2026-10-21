package com.scc.dao;

import com.scc.model.ServiceJob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJobDao {

    // Insert new record using parameterized PreparedStatement (prevents SQL injection)
    public void addJob(ServiceJob job) {
        String sql = "INSERT INTO service_jobs (customer_name, service_type, estimated_cost, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, job.getCustomerName());
            pstmt.setString(2, job.getServiceType());
            pstmt.setDouble(3, job.getEstimatedCost());
            pstmt.setString(4, job.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to insert record: " + e.getMessage());
        }
    }

    // Fetch all records from database
    public List<ServiceJob> getAllJobs() {
        List<ServiceJob> jobs = new ArrayList<>();
        String sql = "SELECT id, customer_name, service_type, estimated_cost, status FROM service_jobs";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = rs.getString("customer_name");
                String serviceType = rs.getString("service_type");
                double estimatedCost = rs.getDouble("estimated_cost");
                String status = rs.getString("status");

                jobs.add(new ServiceJob(id, customerName, serviceType, estimatedCost, status));
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to fetch records: " + e.getMessage());
        }
        return jobs;
    }
}