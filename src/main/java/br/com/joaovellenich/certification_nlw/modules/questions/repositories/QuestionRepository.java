package br.com.joaovellenich.certification_nlw.modules.questions.repositories;

import br.com.joaovellenich.certification_nlw.modules.questions.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
    List<QuestionEntity> findByTech(String tech);
}
