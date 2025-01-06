package org.example;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

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

//    public void setId() {
//        this.id = UUID.randomUUID();
//    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
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
