package com.expert.certification_nlw.modules.students.controllers;

import com.expert.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.expert.certification_nlw.modules.students.useCases.VerifyHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentsController {

  @Autowired private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

  @PostMapping("/verifyIfHasCertification")
  public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO dto) {
    var result = this.verifyHasCertificationUseCase.execute(dto);
    if (result) {
      return "USUÁRIO JÁ FEZ A PROVA!";
    }

    return "USUÁRIO PODE FAZER A PROVA!";
  }
}
