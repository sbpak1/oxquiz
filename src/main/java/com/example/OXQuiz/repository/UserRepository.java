package com.example.OXQuiz.repository;

import com.example.OXQuiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // 로그인용 사용자 ID(String)로 조회
    Optional<UserEntity> findByIdEquals(String id);
}
