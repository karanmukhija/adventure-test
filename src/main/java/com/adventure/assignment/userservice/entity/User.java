package com.adventure.assignment.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Integer id;
    private String userName;
    private String email;
    private String pwd;
    private String role;
}
