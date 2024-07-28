package com.ravin.quizapp.dao;

import com.ravin.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    @Query("select q from Question q where category = :category")
    List<Question> findByCategory(String category);

    @Query(value = "select * from question where category = ?1 order by rand() limit ?2", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, Integer numQ);
}
