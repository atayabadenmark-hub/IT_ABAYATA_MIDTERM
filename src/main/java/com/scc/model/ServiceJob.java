package com.scc.model;

public class ServiceJob {
    private int id;
    private String customerName;
    private String serviceType;
    private double estimatedCost;
    private String status;

    // Constructor with ID (used when fetching from database)
    public ServiceJob(int id, String customerName, String serviceType, double estimatedCost, String status) {
        this.id = id;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.estimatedCost = estimatedCost;
        this.status = status;
    }

    // Constructor without ID (used when creating a new record)
    public ServiceJob(String customerName, String serviceType, double estimatedCost, String status) {
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.estimatedCost = estimatedCost;
        this.status = status;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getServiceType() { return serviceType; }
    public double getEstimatedCost() { return estimatedCost; }
    public String getStatus() { return status; }
}