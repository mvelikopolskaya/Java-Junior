package org.example;


//Настройте связь между вашим приложением и базой данных MySQL с использованием Hibernate.
//Создайте несколько объектов Person и сохраните их в базу данных.

public class Main {
    public static void main(String[] args) {
        DB.createDB();

        Person  person1 = new Person("Anna", 30);
        DB.savePersonToDB(person1);
        Person  person2 = new Person("Sergei", 20);
        DB.savePersonToDB(person2);
        Person  person3 = new Person("Maria", 35);
        DB.savePersonToDB(person3);

        DB.getFromDB();
    }
}