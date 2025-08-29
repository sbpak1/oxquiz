package com.example.OXQuiz.dto;

import com.example.OXQuiz.entity.QuizEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDto {
    private Integer id;

    private String question;
    // 퀴즈 정답 (true: O, false: X)
    private Boolean answer;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> Dto 변환
    public static QuizDto fromEntity(QuizEntity entity) {
        if (entity == null) {
            return null;
        }
        return QuizDto.builder()
                .id(entity.getId())
                .question(entity.getQuestion())
                .answer(entity.getAnswer())
                .writer(entity.getWriter())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    // dto를 entity로 변환
    public QuizEntity toEntity() {
        return QuizEntity.builder()
                .question(this.question)
                .answer(this.answer)
                .writer(this.writer)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}

