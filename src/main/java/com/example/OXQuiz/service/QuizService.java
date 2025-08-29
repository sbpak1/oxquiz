package com.example.OXQuiz.service;

import com.example.OXQuiz.dto.QuizDto;
import com.example.OXQuiz.entity.QuizEntity;
import com.example.OXQuiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Transactional
    public void saveQuiz(QuizDto dto) {
        QuizEntity entity = dto.toEntity();
        quizRepository.save(entity);
    }

    public List<QuizEntity> findAllQuizzes() {
        return quizRepository.findAll();
    }

    public QuizEntity findRandomQuiz() {
        List<QuizEntity> allQuizzes = quizRepository.findAll();
        if (allQuizzes.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(allQuizzes.size());
        return allQuizzes.get(randomIndex);
    }

    public QuizEntity findQuizById(Integer id) {
        Optional<QuizEntity> optionalQuiz = quizRepository.findById(id);
        return optionalQuiz.orElse(null);
    }

    @Transactional
    public void deleteQuizById(Integer id) {
        quizRepository.deleteById(id);
    }
}