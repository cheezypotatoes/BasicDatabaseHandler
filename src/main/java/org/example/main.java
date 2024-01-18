package org.example;

public class main {
    public static void main(String[] args) {


        // Location of database
        String location = "jdbc:sqlite:test.db";

        // Create database class
        database_manager database = new database_manager();

        // Initialize location
        database.init(location);

        // Create Database if not exist
        database.CreateTablesIfNoExist();

        // Insert Data
        database.InsertNewUser("Jorge", "My password", true, 1.40);

        // Returns 2D of all data
        Object[][] all_data = database.GetAllData();

    }
}
