package org.example;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lesson_4.users")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;


    public Person(String name, int age) {
        this.id ++;
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getId()).append("\n");
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Age: ").append(getAge()).append("\n");
        return sb.toString();
    }
}
