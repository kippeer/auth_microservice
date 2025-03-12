package com.authmicroservice.demo.models;

import com.authmicroservice.demo.Enums.ERole;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;
}