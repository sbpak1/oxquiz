package com.example.OXQuiz.service;

import com.example.OXQuiz.dto.PlayDto;
import com.example.OXQuiz.entity.PlayEntity;
import com.example.OXQuiz.entity.UserEntity;
import com.example.OXQuiz.repository.PlayRepository;
import com.example.OXQuiz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlayService {

    private final PlayRepository playRepository;
    private final UserRepository userRepository;

    // 퀴즈 정답 기록을 저장하고 사용자 성적을 업데이트합니다.
    @Transactional
    public boolean savePlayRecord(PlayDto dto) {

        PlayEntity playEntity = PlayDto.toEntity(dto);
        playRepository.save(playEntity);

        // 2. 사용자 ID로 사용자 정보를 조회합니다.
        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserNo());

        if (!optionalUser.isPresent()) {
            // 사용자가 존재하지 않으면 false를 반환하여 실패를 알립니다.
            return false;
        }

        UserEntity user = optionalUser.get();

        // 3. 퀴즈 정답 여부에 따라 사용자 성적을 업데이트합니다.
        if (Boolean.TRUE.equals(dto.getIsCorrect())) {
            // 정답일 경우, 맞힌 횟수를 1 증가시킵니다.
            user.setAnswerTrue(user.getAnswerTrue() + 1);
        } else {
            // 오답일 경우, 틀린 횟수를 1 증가시킵니다.
            user.setAnswerFalse(user.getAnswerFalse() + 1);
        }

        // 4. 업데이트된 사용자 정보를 저장합니다.
        userRepository.save(user);

        return true;
    }
}