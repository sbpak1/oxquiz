package com.example.OXQuiz.service;

import com.example.OXQuiz.dto.QuizDto;
import com.example.OXQuiz.entity.QuizEntity;
import com.example.OXQuiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;


    // 새로운 퀴즈 등록하기
    public QuizEntity insertQuiz(QuizDto dto) {
        QuizEntity entity = QuizDto.toEntity(dto);
        return quizRepository.save(entity);
    }

    // 퀴즈 목록 조회하기
    public List<QuizEntity> findAllQuizzes() {
        return quizRepository.findAll();
    }

    // DB에서 랜덤으로 퀴즈 하나 조회하기
    public QuizEntity findRandomQuiz() {
        List<QuizEntity> allQuizzes = quizRepository.findAll();
        if (allQuizzes.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allQuizzes.size());
        return allQuizzes.get(randomIndex);
    }

    // ID의 퀴즈를 찾아 반환
    public QuizEntity findQuizById(int id) {
        return quizRepository.findById(id).orElse(null);
    }

    // ID의 퀴즈를 삭제
    @Transactional
    public void deleteQuizById(int id) {
        quizRepository.deleteById(id);
    }

    // 퀴즈 업데이트 ID가 존재하면 업데이트 수행
    @Transactional
    public QuizEntity updateQuiz(QuizDto dto) {
        QuizEntity entity = QuizDto.toEntity(dto);
        return quizRepository.save(entity);
    }

}
