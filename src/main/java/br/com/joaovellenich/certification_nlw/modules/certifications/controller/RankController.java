package br.com.joaovellenich.certification_nlw.modules.certifications.controller;

import br.com.joaovellenich.certification_nlw.modules.certifications.useCases.TopTenUseCase;
import br.com.joaovellenich.certification_nlw.modules.student.entities.CertificationStudentEntity;
import br.com.joaovellenich.certification_nlw.modules.student.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private TopTenUseCase topTenUseCase;
    @GetMapping("/top10/{tech}")
    public List<CertificationStudentEntity> topTen(@PathVariable String tech){
        return this.topTenUseCase.execute(tech);
    }
}
