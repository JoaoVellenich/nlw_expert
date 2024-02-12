package br.com.joaovellenich.certification_nlw.modules.student.controller;

import br.com.joaovellenich.certification_nlw.modules.student.dto.StudentCertificationAnswerDTO;
import br.com.joaovellenich.certification_nlw.modules.student.dto.VerifyIfHasCertificationDTO;
import br.com.joaovellenich.certification_nlw.modules.student.entities.CertificationStudentEntity;
import br.com.joaovellenich.certification_nlw.modules.student.useCases.StudentCertificationAnswersUseCase;
import br.com.joaovellenich.certification_nlw.modules.student.useCases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyIfHasCertificationDTO verifyIfHasCertificationDTO){
        boolean result = this.verifyIfHasCertificationUseCase.execute(verifyIfHasCertificationDTO);
        if(result){
            return "User already has certification";
        }else{
            return "User does not have certification";
        }
    }

    @PostMapping("/certificationAnswer")
    public ResponseEntity<Object> certificationAnswer(@RequestBody StudentCertificationAnswerDTO StudentCertificationAnswerDTO) throws Exception {
        try{
            var result = this.studentCertificationAnswersUseCase.execute((StudentCertificationAnswerDTO));
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
