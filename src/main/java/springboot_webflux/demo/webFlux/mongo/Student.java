package springboot_webflux.demo.webFlux.mongo;

import lombok.Data;

import java.io.Serializable;

//@Document
@Data
public class Student implements Serializable {
    //    @Id
    String id;
    String name;
    int age;

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
