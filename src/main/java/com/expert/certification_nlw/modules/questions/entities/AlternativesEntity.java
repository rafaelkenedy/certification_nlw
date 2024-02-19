package com.expert.certification_nlw.modules.questions.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "alternatives")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private boolean isCorrect;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
