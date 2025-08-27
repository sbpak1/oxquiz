package com.example.OXQuiz.dto;

import com.example.OXQuiz.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;
    private String password;
    private boolean status;
    private boolean isAdmin;
    private int answerTrue;
    private int answerFalse;


    // dto를 entity로 변환
    public static UserEntity toEntity(UserDto dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.isStatus());
        entity.setAdmin(dto.isAdmin());
        entity.setAnswerTrue(dto.getAnswerTrue());
        entity.setAnswerFalse(dto.getAnswerFalse());
        return entity;
    }

    // entity를 dto로 변환
    public static UserDto fromEntity(UserEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }

        return UserDto.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .status(entity.isStatus())
                .isAdmin(entity.isAdmin())
                .answerTrue(entity.getAnswerTrue())
                .answerFalse(entity.getAnswerFalse())
                .build();
    }

}
