package br.com.joaovellenich.certification_nlw.modules.student.useCases;

import br.com.joaovellenich.certification_nlw.modules.questions.entities.AlternativesEntity;
import br.com.joaovellenich.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.joaovellenich.certification_nlw.modules.questions.repositories.QuestionRepository;
import br.com.joaovellenich.certification_nlw.modules.student.dto.StudentCertificationAnswerDTO;
import br.com.joaovellenich.certification_nlw.modules.student.dto.VerifyIfHasCertificationDTO;
import br.com.joaovellenich.certification_nlw.modules.student.entities.AnswersCertificationsEntity;
import br.com.joaovellenich.certification_nlw.modules.student.entities.CertificationStudentEntity;
import br.com.joaovellenich.certification_nlw.modules.student.entities.StudentEntity;
import br.com.joaovellenich.certification_nlw.modules.student.repositories.CertificationStudentRepository;
import br.com.joaovellenich.certification_nlw.modules.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
    public CertificationStudentEntity execute(StudentCertificationAnswerDTO studentCertificationAnswerDTO) throws Exception{
        var hasCertification = verifyIfHasCertificationUseCase.execute(new VerifyIfHasCertificationDTO(studentCertificationAnswerDTO.getEmail(), studentCertificationAnswerDTO.getTech()));

        if(hasCertification){
            throw new Exception("Already has certification");
        }

        List<QuestionEntity> questionEntity = this.questionRepository.findByTech(studentCertificationAnswerDTO.getTech());
        List<AnswersCertificationsEntity> answersCertificationsEntities = new ArrayList<>();

        AtomicInteger grade = new AtomicInteger(0);

        studentCertificationAnswerDTO.getQuestionAnswer()
                .forEach(questionAnswer -> {
                    var currentQuestion = questionEntity.stream().filter(question -> question.getId().equals(questionAnswer.getQuestionId())).findFirst().get();
                    var rightAnswer = currentQuestion.getAlternativesEntity().stream().filter(AlternativesEntity::isCorrect).findFirst().get();

                    if(rightAnswer.getId().equals(questionAnswer.getAlternativeId())){
                        grade.incrementAndGet();
                    }

                    questionAnswer.setCorrect(rightAnswer.getId().equals(questionAnswer.getAlternativeId()));

                    var answerCertificationEntity = AnswersCertificationsEntity.builder()
                            .answerID(questionAnswer.getAlternativeId())
                            .questionId(questionAnswer.getQuestionId())
                            .isCorrect(questionAnswer.isCorrect())
                            .build();
                    answersCertificationsEntities.add(answerCertificationEntity);

                });
        var student = studentRepository.findByEmail(studentCertificationAnswerDTO.getEmail());
        UUID studentId;
        if(student.isEmpty()){
            var newStudent = StudentEntity.builder()
                    .email(studentCertificationAnswerDTO.getEmail())
                    .build();
            var createdStudent =  studentRepository.save(newStudent);
            studentId = createdStudent.getId();
        }else {
            studentId = student.get().getId();
        }



        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .tech(studentCertificationAnswerDTO.getTech())
                .studentId(studentId)
                .grade(grade.get())
                .build();

        var certificationCred = certificationStudentRepository.save(certificationStudentEntity);

        answersCertificationsEntities.forEach(answersCertificationsEntity -> {
            answersCertificationsEntity.setCertificationID(certificationStudentEntity.getId());
            answersCertificationsEntity.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationCred.setAnswersCertificationsEntity(answersCertificationsEntities);
        certificationStudentRepository.save(certificationStudentEntity);

        return certificationCred;
    }
}
