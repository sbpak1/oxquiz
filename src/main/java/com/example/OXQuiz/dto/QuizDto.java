package com.example.OXQuiz.dto;

import com.example.OXQuiz.entity.QuizEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {

    private String question;
    // 퀴즈 정답 (true: O, false: X)
    private boolean answer;

    // dto를 entity로 변환
    public static QuizEntity toEntity(QuizDto dto) {
        QuizEntity entity = new QuizEntity();
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.isAnswer());
        return entity;
    }
}

