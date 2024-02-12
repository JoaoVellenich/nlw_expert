package br.com.joaovellenich.certification_nlw.modules.certifications.useCases;

import br.com.joaovellenich.certification_nlw.modules.student.entities.CertificationStudentEntity;
import br.com.joaovellenich.certification_nlw.modules.student.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopTenUseCase {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    public List<CertificationStudentEntity> execute(String tech){
        return this.certificationStudentRepository.findTopTen(tech);
    }
}
