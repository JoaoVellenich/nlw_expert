package br.com.joaovellenich.certification_nlw.modules.student.useCases;

import br.com.joaovellenich.certification_nlw.modules.student.dto.VerifyIfHasCertificationDTO;
import br.com.joaovellenich.certification_nlw.modules.student.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    public boolean execute(VerifyIfHasCertificationDTO dto){
        var result = this.certificationStudentRepository.findByStudentEmailAndTech(dto.getEmail(), dto.getTech());
        return !result.isEmpty();
    }
}
