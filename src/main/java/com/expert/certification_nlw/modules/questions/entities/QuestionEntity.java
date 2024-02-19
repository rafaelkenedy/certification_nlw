package com.expert.certification_nlw.modules.questions.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50)
    String technology;

    String description;

    @OneToMany
    @JoinColumn(name = "question_id")
    private List<AlternativesEntity> alternatives;


    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
