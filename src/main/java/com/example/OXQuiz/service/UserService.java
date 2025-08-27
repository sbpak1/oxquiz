package com.example.OXQuiz.service;


import com.example.OXQuiz.dto.UserDto;
import com.example.OXQuiz.entity.UserEntity;
import com.example.OXQuiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // 사용자 조회하고 dto리스트로 반환
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    // 신규 유저 등록
    // 관리자 계정은 특별 처리
    public boolean insertUser(UserDto dto) {
        // 아이디 중복 확인
        Optional<UserEntity> existingUser = userRepository.findById(dto.getId());
        if (existingUser.isPresent()) {
            return false;
        }
        UserEntity entity = UserDto.toEntity(dto);

        // 관리자 계정은 status와 Admin을 true로 설정
        if ("root".equals(dto.getId()) && "admin".equals(dto.getPassword())) {
            entity.setStatus(true);
            entity.setAdmin(true);
        } else {
            // 일반 사용자 계정은 false로 설정
            entity.setStatus(false);
            entity.setAdmin(false);
        }

        // DB에 저장
        userRepository.save(entity);
        return true;
    }


    // 유저 정보 업데이트
    public void updateUser(UserDto dto) {
        UserEntity entity = UserDto.toEntity(dto);
        userRepository.save(entity);
    }

    // 유저 아이디로 정보 조회하기
    public UserDto findUser(String userId) {
        UserEntity entity = userRepository.findById(userId).orElse(null);
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        return UserDto.fromEntity(entity);
    }

    // 유저 삭제하기
    public void deleteUser(String userId) {
        UserEntity entity = userRepository.findById(userId).orElse(null);
        if (!ObjectUtils.isEmpty(entity)) {
            userRepository.delete(entity);
        }
    }

    // 유저 비밀번호 수정하기
    public boolean updatePassword(String id, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    // 유저 status를 승인으로 변경하기
    public boolean approveUserStatus(String id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setStatus(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }



}
