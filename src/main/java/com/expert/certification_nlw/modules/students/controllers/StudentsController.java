package com.expert.certification_nlw.modules.students.controllers;

import com.expert.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.expert.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;

import com.expert.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.expert.certification_nlw.modules.students.useCases.StudentCertificationAnswerUseCase;
import com.expert.certification_nlw.modules.students.useCases.VerifyHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentsController {

  @Autowired private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

  @Autowired private StudentCertificationAnswerUseCase studentCertificationAnswerUseCase;

  @PostMapping("/verifyIfHasCertification")
  public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO dto) {
    var result = this.verifyHasCertificationUseCase.execute(dto);
    if (result) {
      return "USUÁRIO JÁ FEZ A PROVA!";
    }

    return "USUÁRIO PODE FAZER A PROVA!";
  }

  @PostMapping("/certification/answer")
  public ResponseEntity<Object> certificationAnswer(
      @RequestBody StudentCertificationAnswerDTO dto) {
    try{
      var result = this.studentCertificationAnswerUseCase.execute(dto);
       return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
