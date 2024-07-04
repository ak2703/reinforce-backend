package me.akshayvilekar.reinforce_backend.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "users")
@Builder
public class User {
    @Id
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roles; // Store roles as a comma-separated string
    @CreatedDate
    private LocalDateTime createdOn;
    @LastModifiedDate
    private LocalDateTime updatedOn;
}
