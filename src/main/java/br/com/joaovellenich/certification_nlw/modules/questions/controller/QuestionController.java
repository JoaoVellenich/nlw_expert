package br.com.joaovellenich.certification_nlw.modules.questions.controller;

import br.com.joaovellenich.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import br.com.joaovellenich.certification_nlw.modules.questions.dto.QuestionResultDTO;
import br.com.joaovellenich.certification_nlw.modules.questions.entities.AlternativesEntity;
import br.com.joaovellenich.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.joaovellenich.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @GetMapping("/tech/{tech}")
    public List<QuestionResultDTO> findByTech(@PathVariable  String tech){
        var result = this.questionRepository.findByTech((tech));
        return result.stream().map(QuestionController::mapQuestionToDTO).collect(Collectors.toList());
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity questions){
        var questionResultDTO = QuestionResultDTO.builder()
                .description(questions.getDescription())
                .id(questions.getId())
                .tech(questions.getTech())
                .build();

        List<AlternativesResultDTO> alternativesResultDTOS =
                questions.getAlternativesEntity().stream().map((QuestionController::mapAlternativeToDTO)).collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativesResultDTOS);
        return questionResultDTO;
    }

    static AlternativesResultDTO mapAlternativeToDTO(AlternativesEntity alternatives){
        return AlternativesResultDTO.builder()
                .description(alternatives.getDescription())
                .id(alternatives.getId())
                .build();
    }
}
