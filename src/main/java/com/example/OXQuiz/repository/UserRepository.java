package com.example.OXQuiz.repository;

import com.example.OXQuiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByIdAndPassword(String id, String password);
    Optional<UserEntity> findById(String id);

}
