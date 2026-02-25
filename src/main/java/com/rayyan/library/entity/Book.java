package com.rayyan.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Column(nullable = false)
    private String author;

    @NotNull(message = "price is required")
    @Positive(message = "price should be greater than 0")
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "borrowed_by")
    private User borrowedBy;

}
