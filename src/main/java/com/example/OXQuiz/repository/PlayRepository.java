package com.example.OXQuiz.repository;


import com.example.OXQuiz.entity.PlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayRepository extends JpaRepository<PlayEntity, Integer> {

}
