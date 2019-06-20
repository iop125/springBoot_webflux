package springboot_webflux.demo.webFlux.mongo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Document
@Data
public class Student implements Serializable {
    @Id
    String id;
    @NotBlank(message="姓名不能为null")
    String name;
    @Range(min=10,max=50 ,message = "年龄在10到50岁")
    int age;

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student() {
    }

}
