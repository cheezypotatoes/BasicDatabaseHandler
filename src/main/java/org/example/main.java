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

        // Insert User
        database.InsertNewUser("Jorge", "My password", true, 1.40);

        // Returns 2D of user data
        Object[][] all_data = database.SeeAllUserData();

        // Insert Book
        database.InsertNewBook("Sample Book", "This is a sample book.", "sample.jpg", "Fiction", 1, true, 19.99, 0);

        // Show all books
        database.ShowAllBook();

        // Returns the detail of the book
        String book_details = database.ReturnBookDescription(3);

    }
}