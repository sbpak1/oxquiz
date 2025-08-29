package com.example.OXQuiz.dto;

import com.example.OXQuiz.entity.PlayEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayDto {

    private Long id;
    private Integer quizId;
    private Integer userNo;
    private Boolean isCorrect;
    private LocalDateTime createdAt;   // 플레이한 시간
    private LocalDateTime updatedAt;

    public static PlayDto fromEntity(PlayEntity entity) {
        return PlayDto.builder()
                .id(entity.getId())
                .userNo(entity.getUserNo())
                .quizId(entity.getQuizId())
                .isCorrect(entity.getIsCorrect())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // DTO → Entity
    public static PlayEntity toEntity(PlayDto dto) {
        return PlayEntity.builder()
                .id(dto.getId())
                .userNo(dto.getUserNo())
                .quizId(dto.getQuizId())
                .isCorrect(dto.getIsCorrect())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

}