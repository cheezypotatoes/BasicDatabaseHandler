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
        //database.InsertNewUser("jorge1@example.com", "Jorge", "My password", true, 5000, "");

        // Returns 2D of user data
        //Object[][] all_data = database.SeeAllUserData();

        // Insert Book
        //database.InsertNewBook("New Scary book_4444", "sample.jpg", "Comedy,Horror", 1, true, 19.99, 0, "description test 2");

        // Return book description
        //String desc = database.returnBookDescriptionById(1);
        //System.out.println(desc);

        // Show all books
        //database.ReturnAllBooks();

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


        // Buy books
        //database.BuyBook(150, 2, 3);

        // Get specific data of user
        //String[] data = database.ReturnUserDetailsById(1);

        //for (int i = 0; i < data.length; i++){
            //System.out.println(data[i]);
        //}


        //String[] booksBought = database.ReturnBooksBoughtById(1);

        //for (int i = 0; i < booksBought.length; i++){
            //System.out.printf("books #%d = %s\n", i, booksBought[i]);
        //}



        //String[] top_3 = database.ReturnTopThreeBooks();

        //for (int i = 0; i < top_3.length; i++){
            //System.out.println(top_3[i]);
        //}

        // Returns Id If account in database
        //System.out.printf("User id = %d", database.ReturnUserIdByLogIn("Jorge", "My password"));




        // Query Builder
        List<Object[]> searchResults = database.searchBookUsingQuery("ORDER BY book_sold DESC LIMIT 3;");

        for (Object[] bookDetails : searchResults) {
            System.out.println("Book ID: " + bookDetails[0]);
            System.out.println("Title: " + bookDetails[1]);
            System.out.println("Description: " + bookDetails[2]);
            System.out.println("Image Link: " + bookDetails[3]);
            System.out.println("Genre: " + bookDetails[4]);
            System.out.println("Author ID: " + bookDetails[5]);
            System.out.println("Availability: " + bookDetails[6]);
            System.out.println("Book Price: " + bookDetails[7]);
            System.out.println("Books Sold: " + bookDetails[8]);
            System.out.println();
        }








    }
}
