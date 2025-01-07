package org.example;

import org.hibernate.Session;

import java.sql.*;
import java.util.List;

public class DB {
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    private static Connector connector = new Connector();


    public static void createDB() {
        try {
            Connection connection = DriverManager.getConnection(url, USER, PASSWORD);
            createDatabase(connection);
            createTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void savePersonToDB(Object ob) {
        Session session = connector.getSession();
        session.beginTransaction();
        session.save(ob);
        session.getTransaction().commit();
        session.close();
    }

    public static void getFromDB() {
        try (Session session = connector.getSession()) {
            List<Person> users = session.createQuery("FROM Person", Person.class).getResultList();
            users.forEach(System.out::println);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase(Connection connection) {
        String createDatabase =  "CREATE DATABASE IF NOT EXISTS lesson_4";
        try (PreparedStatement statement = connection.prepareStatement(createDatabase)) {
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS lesson_4.users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT)";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }
}
