package org.example;

import java.util.List;

@SuppressWarnings("ALL")
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
        //database.InsertNewUser("Jorge", "My password", true, 1.40);

        // Returns 2D of user data
        //Object[][] all_data = database.SeeAllUserData();

        // Insert Book
        //database.InsertNewBook("New Scary book_3 but with comedy", "sample.jpg", "Comedy,Horror", 1, true, 19.99, 0, "description test 2");

        // Return book description
        //String desc = database.returnBookDescription(2);
        //System.out.println(desc);

        // Show all books
        //database.ShowAllBook();

        // Returns the detail of the book
        //List<Object> book_details = database.ReturnBookDetailsById  (2);
        //for (Object detail : book_details) {
            //System.out.println(detail);
        //}


        // Return the name of the author from userdata table
        //String author_name = database.ReturnAuthorNameById(1);
        //System.out.println(author_name);


        // Return book it by its genre
        //String[] genres = {"Comedy"};
        //List<String> result = database.ReturnIdByGenre(genres);

        // Process the result
        //for (String id : result) {
            //System.out.println(id);
        //}



    }
}
