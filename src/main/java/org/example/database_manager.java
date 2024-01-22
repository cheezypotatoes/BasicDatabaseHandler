package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class database_manager {

    public String data_location;

    public void init (String data_location) {
        this.data_location = data_location;
    }

    public void CreateTablesIfNoExist(){

        try (Connection connection = DriverManager.getConnection(this.data_location);
             Statement statement = connection.createStatement()) {

            // SQL statement to create a table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS user_data ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "password TEXT NOT NULL,"
                    + "admin BOOLEAN DEFAULT 0,"
                    + "balance REAL DEFAULT 0.0"
                    + ");";

            String createBookDetailsTableSQL = "CREATE TABLE IF NOT EXISTS book_details ("
                    + "book_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title TEXT NOT NULL,"
                    + "image_link TEXT,"
                    + "genre TEXT,"
                    + "author_id INTEGER,"
                    + "availability BOOLEAN DEFAULT 1,"
                    + "book_price REAL DEFAULT 0.0,"
                    + "book_sold INTEGER DEFAULT 0,"
                    + "FOREIGN KEY (author_id) REFERENCES user_data (id)"
                    + ");";

            String createBookDescriptionSQL = "CREATE TABLE IF NOT EXISTS book_description ("
                    + "description_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "description TEXT"
                    + ");";


            String createAuthorTableSQL = "CREATE TABLE IF NOT EXISTS author ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "user_id INTEGER,"
                    + "description TEXT,"
                    + "book_pending TEXT,"
                    + "book_published TEXT,"
                    + "FOREIGN KEY (user_id) REFERENCES user_data (id)"
                    + ");";


            // Execute the SQL statement
            statement.execute(createTableSQL);
            statement.execute(createBookDescriptionSQL);
            statement.execute(createBookDetailsTableSQL);
            statement.execute(createAuthorTableSQL);

            System.out.println("Table created successfully (if not existed)");

        } catch (SQLException e) {
            e.printStackTrace();}

    }

    public void InsertNewUser(String name, String password, Boolean isAdmin, double balance){
        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            // SQL statement to insert data into the "user_data" table
            String insertUserDataSQL = "INSERT INTO user_data (name, password, admin, balance) VALUES (?, ?, ?, ?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserDataSQL)) {
                // Set values for the parameters in the prepared statement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setBoolean(3, isAdmin);
                preparedStatement.setDouble(4, balance);

                // Execute the SQL statement to insert data
                preparedStatement.executeUpdate();

                System.out.println("Data inserted into user_data table successfully");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertNewBook(String title, String imageLink, String genre, int authorId,
                              boolean availability,
                              double bookPrice,
                              int bookSold,
                              String description){
        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            // SQL statement to insert data into the "book_details" table
            String insertBookDetailsSQL = "INSERT INTO book_details (title, image_link, genre, author_id, availability, book_price, book_sold) VALUES (?, ?, ?, ?, ?, ?, ?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookDetailsSQL)) {
                // Set values for the parameters in the prepared statement
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, imageLink);
                preparedStatement.setString(3, genre);
                preparedStatement.setInt(4, authorId);
                preparedStatement.setBoolean(5, availability);
                preparedStatement.setDouble(6, bookPrice);
                preparedStatement.setInt(7, bookSold);

                // Execute the SQL statement to insert data
                preparedStatement.executeUpdate();

                System.out.println("Data inserted into book_details table successfully");

                InsertNewDescription(description);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertNewDescription(String description){
        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            // SQL statement to insert data into the "book_details" table
            String insertBookDetailsSQL = "INSERT INTO book_description (description) VALUES (?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookDetailsSQL)) {
                // Set values for the parameters in the prepared statement
                preparedStatement.setString(1, description);

                // Execute the SQL statement to insert data
                preparedStatement.executeUpdate();

                System.out.println("Data inserted into book_description table successfully");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ShowAllBook(){
        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            // SQL statement to retrieve all data from the "book_details" table
            String retrieveBooksSQL = "SELECT * FROM book_details;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveBooksSQL)) {
                // Execute the SQL statement and get the result set
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the result set and print the book details
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String imageLink = resultSet.getString("image_link");
                    String genre = resultSet.getString("genre");
                    int authorId = resultSet.getInt("author_id");
                    boolean availability = resultSet.getBoolean("availability");
                    double bookPrice = resultSet.getDouble("book_price");
                    int bookSold = resultSet.getInt("book_sold");

                    // Print or process the retrieved book details as needed
                    System.out.println("Book ID: " + bookId);
                    System.out.println("Title: " + title);
                    System.out.println("Description: " + description);
                    System.out.println("Image Link: " + imageLink);
                    System.out.println("Genre: " + genre);
                    System.out.println("Author ID: " + authorId);
                    System.out.println("Availability: " + availability);
                    System.out.println("Book Price: " + bookPrice);
                    System.out.println("Books Sold: " + bookSold);
                    System.out.println();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String returnBookDescription(int bookId) {
        String result = null;

        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            String retrieveDetailsSQL = "SELECT * FROM book_description WHERE description_id = ?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveDetailsSQL)) {
                preparedStatement.setInt(1, bookId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Assuming 'description' is the column name in your table
                    result = resultSet.getString("description");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Object> ReturnBookDetailsById(int bookId){
        List<Object> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            String retrieveDetailsSQL = "SELECT * FROM book_details WHERE book_id = ?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveDetailsSQL)) {
                preparedStatement.setInt(1, bookId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result.add(resultSet.getString("title"));
                    result.add(resultSet.getString("description"));
                    result.add(resultSet.getString("image_link"));
                    result.add(resultSet.getString("genre"));
                    result.add(resultSet.getInt("author_id"));
                    result.add(resultSet.getBoolean("availability"));
                    result.add(resultSet.getDouble("book_price"));
                    result.add(resultSet.getInt("book_sold"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String ReturnAuthorNameById(int user_id){

        String authorName = "";

        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            String retrieveDetailsSQL = "SELECT name FROM user_data WHERE id = ?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveDetailsSQL)) {
                preparedStatement.setInt(1, user_id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    authorName = resultSet.getString("name");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authorName;

    }



    public Object[][] SeeAllUserData(){
        List<Object[]> dataList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(this.data_location)) {
            // SQL statement to retrieve all data from the "user_data" table
            String retrieveUserDataSQL = "SELECT * FROM user_data;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveUserDataSQL)) {
                // Execute the SQL statement and get the result set
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the result set and add data to the list
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    boolean isAdmin = resultSet.getBoolean("admin");
                    double balance = resultSet.getDouble("balance");

                    // Add the retrieved data to the list
                    dataList.add(new Object[]{id, name, password, isAdmin, balance});
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Convert the list to a 2D array
        return dataList.toArray(new Object[0][0]);
    }

    public List<String> ReturnIdByGenre(String[] genres) {
        List<String> result = new ArrayList<>();
        String query = "SELECT book_id FROM book_details WHERE " + buildCondition(genres);

        try (Connection connection = DriverManager.getConnection(data_location);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                result.add(String.valueOf(bookId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String buildCondition(String[] genres){
        StringBuilder condition = new StringBuilder();

        for (String genre : genres) {
            condition.append("genre LIKE '%").append(genre).append("%' OR ");
        }

        if (condition.length() > 0) {
            // Remove the last " OR "
            condition.setLength(condition.length() - 4);
        }

        return condition.toString();

    }


}