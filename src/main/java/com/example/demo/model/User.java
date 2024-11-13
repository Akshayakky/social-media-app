package com.example.demo.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    String firstName;
    String lastName;
    String profileImageUrl;
    UserType type;

    enum UserType {
        COMMITTED_PARENT("Committed Parent"),
        NEW_PARENT("New Parent"),
        PARENT_MENTOR("Parent Mentor");

        private final String type;

        UserType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
