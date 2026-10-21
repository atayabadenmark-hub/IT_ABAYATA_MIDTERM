package com.scc.ui;

import com.scc.dao.ServiceJobDao;
import com.scc.model.ServiceJob;

import java.util.List;
import java.util.Scanner;

public class ServiceJobMenu {
    private final ServiceJobDao jobDao;
    private final Scanner scanner;

    public ServiceJobMenu(ServiceJobDao jobDao) {
        this.jobDao = jobDao;
        this.scanner = new Scanner(System.in);
    }

    public void startMenu() {
        int choice = 0;
        do {
            printHeader();
            System.out.println("[1] Add Record");
            System.out.println("[2] View Records");
            System.out.println("[3] Exit System");
            System.out.print("Enter your choice (1-3): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("\n[ERROR] Invalid input. Please enter a number between 1 and 3.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    viewRecords();
                    break;
                case 3:
                    System.out.println("\nExiting system. Goodbye!");
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice. Choose between 1 and 3.");
            }

            if (choice != 3) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 3);
    }

    private void printHeader() {
        System.out.println("==================================================");
        System.out.println("         AUTO REPAIR SERVICE JOB SYSTEM           ");
        System.out.println("==================================================");
    }

    private void addRecord() {
        System.out.println("\n--- ADD NEW SERVICE JOB ---");

        System.out.print("Enter Customer Name : ");
        String customerName = scanner.nextLine().trim();
        while (customerName.isEmpty()) {
            System.out.print("Customer name cannot be blank. Enter Customer Name: ");
            customerName = scanner.nextLine().trim();
        }

        String serviceType = "";
        boolean validService = false;
        while (!validService) {
            System.out.print("Enter Service Type [OIL_CHANGE, BRAKE_REPAIR, TUNE_UP]: ");
            serviceType = scanner.nextLine().trim().toUpperCase();
            if (serviceType.equals("OIL_CHANGE") || serviceType.equals("BRAKE_REPAIR") || serviceType.equals("TUNE_UP")) {
                validService = true;
            } else {
                System.out.println("[ERROR] Invalid choice. Select OIL_CHANGE, BRAKE_REPAIR, or TUNE_UP.");
            }
        }

        double estimatedCost = -1;
        while (estimatedCost < 0) {
            System.out.print("Enter Estimated Cost S1: ");
            if (scanner.hasNextDouble()) {
                estimatedCost = scanner.nextDouble();
                scanner.nextLine();
                if (estimatedCost < 0) {
                    System.out.println("[ERROR] Cost cannot be negative.");
                }
            } else {
                System.out.println("[ERROR] Please enter a valid numeric value.");
                scanner.next();
            }
        }

        String status = "";
        boolean validStatus = false;
        while (!validStatus) {
            System.out.print("Enter Status [RECEIVED, IN_PROGRESS, READY]: ");
            status = scanner.nextLine().trim().toUpperCase();
            if (status.equals("RECEIVED") || status.equals("IN_PROGRESS") || status.equals("READY")) {
                validStatus = true;
            } else {
                System.out.println("[ERROR] Invalid status. Select RECEIVED, IN_PROGRESS, or READY.");
            }
        }

        ServiceJob newJob = new ServiceJob(customerName, serviceType, estimatedCost, status);
        jobDao.addJob(newJob);
        System.out.println("\n[SUCCESS] New record successfully inserted into database!");
    }

    private void viewRecords() {
        System.out.println("\n----------------------------------------------------------------------------------");
        System.out.println("                                ACTIVE SERVICE JOBS                               ");
        System.out.println("----------------------------------------------------------------------------------");

        List<ServiceJob> jobs = jobDao.getAllJobs();

        if (jobs.isEmpty()) {
            System.out.println("No records found in the database.");
            System.out.println("----------------------------------------------------------------------------------");
            return;
        }

        System.out.printf("%-5s | %-20s | %-15s | %-15s | %-12s%n", "ID", "CUSTOMER NAME", "SERVICE TYPE", "EST. COST", "STATUS");
        System.out.println("----------------------------------------------------------------------------------");

        for (ServiceJob job : jobs) {
            System.out.printf("%-5d | %-20s | %-15s | %-15.2f | %-12s%n",
                    job.getId(),
                    job.getCustomerName(),
                    job.getServiceType(),
                    job.getEstimatedCost(),
                    job.getStatus());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }
}