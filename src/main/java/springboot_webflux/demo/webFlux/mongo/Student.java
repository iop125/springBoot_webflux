package springboot_webflux.demo.webFlux.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {
    @Id
    String id;
    String name;
    int age;
}
