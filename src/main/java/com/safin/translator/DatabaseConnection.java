package com.safin.translator;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.Types.NULL;

/**
 * Created by vitalii.safin on 2/2/2017.
 */
public class DatabaseConnection {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private MyHashMap<String, String> dictionary;
    private String url = "jdbc:mysql://localhost:3306/vocabularydb?useSSL=false";
    private String user = "root";
    private String password = "Rosha#2623";

    DatabaseConnection(MyHashMap dictionary) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e){}
        this.dictionary = dictionary;
        fillFromDatabase();
    }

    void updateData(String text, String translation) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(
                    "UPDATE words SET translation = (?) WHERE word = (?)");
            statement.setString(1, translation);
            statement.setString(2, text);
            statement.executeUpdate();
            System.out.println("Translation of " + text + "replaced with " + translation + " " + statement.toString());
        } catch (SQLException ex) {
            System.out.println("SQLException caught in insertData()" + ex.toString());
        } catch (Exception ex) {
            System.out.println("Error during updating database!" + ex.toString());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(IOProcessor.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    void insertData(String text, String translation) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(
                    "INSERT INTO words VALUES(?,?,?)");
            statement.setInt(1, NULL);
            statement.setString(2, text);
            statement.setString(3, translation);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException caught in insertData()" + ex.toString());
        } catch (Exception ex) {
            System.out.println("Error during updating database!" + ex.toString());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(IOProcessor.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    boolean existsInDatabase(String text) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement("SELECT * FROM words WHERE word=(?)");
            statement.setString(1, text);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(IOProcessor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            System.out.println(ex.getClass().getCanonicalName() + ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(IOProcessor.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
                System.out.println("In closing connection : " + ex.getClass().getCanonicalName() + ex);
            }
        }
        return false;
    }

    private void fillFromDatabase() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement("SELECT * FROM words");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dictionary.put(resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(IOProcessor.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            System.out.println(ex.getClass().getCanonicalName() + ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(IOProcessor.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
                System.out.println("In closing connection : " + ex.getClass().getCanonicalName() + ex);
            }
        }
    }

}
