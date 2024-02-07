package com.expert.certification_nlw.modules.students.useCases;

import com.expert.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyHasCertificationUseCase {

  public boolean execute(VerifyHasCertificationDTO dto) {
      return dto.getEmail().equals("rafael@rafael.com") && dto.getTechnology().equals("JAVA");
  }
}
