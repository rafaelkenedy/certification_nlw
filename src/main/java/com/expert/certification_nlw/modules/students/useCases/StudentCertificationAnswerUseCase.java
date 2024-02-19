package com.expert.certification_nlw.modules.students.useCases;

import com.expert.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.expert.certification_nlw.modules.questions.entities.QuestionEntity;
import com.expert.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.expert.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.expert.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.expert.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.expert.certification_nlw.modules.students.entities.StudentEntity;
import com.expert.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.expert.certification_nlw.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCertificationAnswerUseCase {
  @Autowired private QuestionRepository questionRepository;

  @Autowired private StudentRepository studentRepository;

  @Autowired private CertificationStudentRepository certificationStudentRepository;

  public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) {
    List<QuestionEntity> questions = questionRepository.findByTechnology(dto.getTechnology());
    List<AnswersCertificationsEntity> answerCertifications = new ArrayList<>();
    Map<UUID, QuestionEntity> questionMap =
        questions.stream().collect(Collectors.toMap(QuestionEntity::getId, Function.identity()));

    AtomicInteger correctAnswers = new AtomicInteger(0);

    dto.getQuestionsAnswer()
        .forEach(
            questionAnswer -> {
              QuestionEntity qstn = questionMap.get(questionAnswer.getQuestionId());
              if (qstn == null) {
                throw new IllegalStateException(
                    "Question not found, id: " + questionAnswer.getQuestionId());
              }
              qstn.getAlternatives().stream()
                  .filter(AlternativesEntity::isCorrect)
                  .findFirst()
                  .ifPresent(
                      correctAlternative -> {
                        boolean isCorrect =
                            correctAlternative.getId().equals(questionAnswer.getAlternativeId());
                        questionAnswer.setCorrect(isCorrect);
                        if (isCorrect) {
                          correctAnswers.incrementAndGet();
                        }
                      });

              var answerCertificationEntity =
                  AnswersCertificationsEntity.builder()
                      .answerID(questionAnswer.getAlternativeId())
                      .questionID(questionAnswer.getQuestionId())
                      .isCorrect(questionAnswer.isCorrect())
                      .build();

              answerCertifications.add(answerCertificationEntity);
            });

    var student = studentRepository.findByEmail(dto.getEmail());
    UUID studentId;
    if (student.isEmpty()) {
      var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
      studentCreated = studentRepository.save(studentCreated);
      studentId = studentCreated.getId();
    } else {
      studentId = student.get().getId();
    }

    CertificationStudentEntity certificationStudentEntity =
        CertificationStudentEntity.builder()
            .technology(dto.getTechnology())
            .studentID(studentId)
            .answersCertificationsEntities(answerCertifications)
            .build();

    var certificationStudentCreated =
        certificationStudentRepository.save(certificationStudentEntity);

    answerCertifications.forEach(
        answersCertificationsEntity -> {
          answersCertificationsEntity.setAnswerID(certificationStudentEntity.getId());
          answersCertificationsEntity.setCertificationStudentEntity(certificationStudentEntity);
        });

    certificationStudentEntity.setAnswersCertificationsEntities(answerCertifications);

    certificationStudentRepository.save(certificationStudentEntity);

    return certificationStudentCreated;
  }
}
