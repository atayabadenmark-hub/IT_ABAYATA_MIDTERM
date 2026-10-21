package com.scc;

import com.scc.dao.DatabaseConnection;
import com.scc.dao.ServiceJobDao;
import com.scc.ui.ServiceJobMenu;

public class Main {
    public static void main(String[] args) {
        // Initialize SQLite database table automatically on startup
        DatabaseConnection.initializeDatabase();

        // Instantiate DAO and UI Menu classes
        ServiceJobDao jobDao = new ServiceJobDao();
        ServiceJobMenu menu = new ServiceJobMenu(jobDao);

        // Launch the console application loop
        menu.startMenu();
    }
}