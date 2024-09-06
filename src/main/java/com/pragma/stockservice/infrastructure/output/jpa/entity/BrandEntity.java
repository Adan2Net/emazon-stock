package com.pragma.stockservice.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brands")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 120, message = "Description cannot exceed 120 characters")
    @Pattern(regexp = "^(?!\\s*$)[a-zA-Z\\s]+$", message = "Description must contain only letters and spaces, and cannot be empty or just spaces")
    private String description;
}
