package br.com.joaovellenich.certification_nlw.modules.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyIfHasCertificationDTO {
    private String email;
    private String tech;
}
