package com.expert.certification_nlw.modules.questions.controllers;

import com.expert.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.expert.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.expert.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.expert.certification_nlw.modules.questions.entities.QuestionEntity;
import com.expert.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping("/technology/{technology}")
  public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
    System.out.println("TECH === " + technology);
    var result = this.questionRepository.findByTechnology(technology);

      return result.stream().map(QuestionController::mapQuestionToDTO)
            .collect(Collectors.toList());
  }

  static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
    var questionResultDTO = QuestionResultDTO.builder()
            .id(question.getId())
            .technology(question.getTechnology())
            .description(question.getDescription()).build();

    List<AlternativesResultDTO> alternativesResultDTOs = question.getAlternatives()
            .stream().map(QuestionController::mapAlternativeDTO)
            .collect(Collectors.toList());

    questionResultDTO.setAlternatives(alternativesResultDTOs);
    return questionResultDTO;
  }

  static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternativesResultDTO) {
    return AlternativesResultDTO.builder()
            .id(alternativesResultDTO.getId())
            .description(alternativesResultDTO.getDescription()).build();
  }
}
