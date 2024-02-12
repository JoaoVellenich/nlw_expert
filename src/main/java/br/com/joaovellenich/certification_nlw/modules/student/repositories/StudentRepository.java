package br.com.joaovellenich.certification_nlw.modules.student.repositories;

import br.com.joaovellenich.certification_nlw.modules.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    public Optional<StudentEntity> findByEmail(String email);
}
