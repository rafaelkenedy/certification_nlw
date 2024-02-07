package com.expert.certification_nlw.modules.students.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCertificationsEntity {
  private UUID ID;
  private UUID certificationId;
  private UUID studentId;
  private UUID questionId;
  private boolean isCorrect;
}
