package com.example.OXQuiz.repository;

import com.example.OXQuiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {

}
