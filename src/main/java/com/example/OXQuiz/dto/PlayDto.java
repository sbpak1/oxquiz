package com.example.OXQuiz.dto;

import com.example.OXQuiz.entity.PlayEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayDto {

    private int userNo;
    private boolean isCorrect;
    
    // dto를 entity로 변환
    public static PlayEntity toEntity(PlayDto dto) {
        PlayEntity entity = new PlayEntity();
        entity.setUserNo(dto.getUserNo());
        entity.setCorrect(dto.isCorrect());
        return entity;
    }
}
