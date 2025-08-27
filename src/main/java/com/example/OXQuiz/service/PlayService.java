package com.example.OXQuiz.service;


import com.example.OXQuiz.dto.PlayDto;
import com.example.OXQuiz.entity.PlayEntity;
import com.example.OXQuiz.entity.UserEntity;
import com.example.OXQuiz.repository.PlayRepository;
import com.example.OXQuiz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class PlayService {

    private final PlayRepository playRepository;
    private final UserRepository userRepository;

    // 퀴즈 정답 기록을 저장하고 성적 업데이트 담당
    @Transactional
    public boolean savePlayRecord(PlayDto dto) {
        PlayEntity playEntity = PlayDto.toEntity(dto);
        playRepository.save(playEntity);

        UserEntity user = userRepository.findById(dto.getUserNo()).orElse(null);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }

        if (dto.isCorrect()) {
            user.setAnswerTrue(user.getAnswerTrue() + 1);
        } else {
            user.setAnswerFalse(user.getAnswerFalse() + 1);
        }
        userRepository.save(user);

        return true;
    }
}
