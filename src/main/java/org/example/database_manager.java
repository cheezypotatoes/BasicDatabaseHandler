package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
                    + "description TEXT,"
                    + "image_link TEXT,"
                    + "genre TEXT,"
                    + "author_id INTEGER,"
                    + "availability BOOLEAN DEFAULT 1,"
                    + "book_price REAL DEFAULT 0.0,"
                    + "book_sold INTEGER DEFAULT 0,"
                    + "FOREIGN KEY (author_id) REFERENCES user_data (id)"
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

    public Object[][] GetAllData(){
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
}