package me.akshayvilekar.reinforce_backend.person;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Person {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
