//Задание 1: Создайте класс Person с полями name и age.
//Реализуйте сериализацию и десериализацию этого класса в файл.

//Задание 2: Используя JPA, создайте базу данных для хранения объектов класса Person.
//Реализуйте методы для добавления, обновления и удаления объектов Person.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Anna", 30);
        savePerson("src/person.bin", person1);

        Person person2 = loadPerson("src/person.bin");
        System.out.println(person2);
    }

    static void savePerson(String filePath, Person person) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(person);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Person loadPerson(String filePath) {
        Person person = new Person();
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            person = (Person) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return person;
    }
}