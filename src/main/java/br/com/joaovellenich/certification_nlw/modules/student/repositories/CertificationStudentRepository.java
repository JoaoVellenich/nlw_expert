package br.com.joaovellenich.certification_nlw.modules.student.repositories;

import br.com.joaovellenich.certification_nlw.modules.student.entities.CertificationStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CertificationStudentRepository extends JpaRepository<CertificationStudentEntity, UUID> {

    @Query("SELECT c FROM certifications c INNER JOIN c.studentEntity std WHERE std.email = :email AND c.tech = :tech")
    List<CertificationStudentEntity> findByStudentEmailAndTech(String email, String tech);

    @Query("SELECT c FROM certifications c WHERE c.tech = :tech ORDER BY c.grade DESC LIMIT 10 ")
    List<CertificationStudentEntity> findTopTen(String tech);

}
