package org.example;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class DB {
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    private static Connector connector = new Connector();
    private static Session session = connector.getSession();


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
        try {
            session.beginTransaction();
            session.save(ob);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getFromDB() {
        try {
            List<Person> users = session.createQuery("FROM Person", Person.class).getResultList();
            users.forEach(System.out::println);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePersonFromDB(Person person) {
        try {
            session.beginTransaction();
            session.delete(person);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updatePersonName(Person person, String newData) {
        try {
            String hql = "from Person";
            Query<Person> query = session.createQuery( hql, Person.class);
            query.setParameter("name", newData);
            query.executeUpdate();
//            person.setName(newData);
            session.beginTransaction();
//            session.update(person);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePersonAge(Person person, int newData) {
        try {
            String hql = "FROM Person WHERE id = :id";
            Query<Person> query = session.createQuery( hql, Person.class);
            query.setParameter("age", newData);
            query.executeUpdate();
//            person.setAge(newData);
            session.beginTransaction();
//            session.update(person);
            session.getTransaction().commit();
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
