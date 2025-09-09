package com.vakya.questionServices.repository;

import com.vakya.questionServices.model.Question;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT id FROM question WHERE category = :category ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(@Param("category") String category, @Param("count") int count);

    Question findById(Integer id);

}
