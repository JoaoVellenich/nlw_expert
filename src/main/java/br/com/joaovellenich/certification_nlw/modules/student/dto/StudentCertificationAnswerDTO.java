package br.com.joaovellenich.certification_nlw.modules.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCertificationAnswerDTO {
    private String email;
    private String tech;
    private List<QuestionAnswerDTO> questionAnswer;
}
