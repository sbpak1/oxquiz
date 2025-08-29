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
    public boolean insertUser(UserDto dto) {
        // 아이디 중복 확인
        Optional<UserEntity> existingUser = userRepository.findByIdEquals(dto.getId());
        if (existingUser.isPresent()) {
            return false;
        }

        UserEntity entity = UserDto.toEntity(dto);

        // 관리자 계정 처리
        if ("root".equals(dto.getId()) && "admin".equals(dto.getPassword())) {
            entity.setStatus(true);
            entity.setAdmin(true);
        } else {
            entity.setStatus(false);
            entity.setAdmin(false);
        }

        userRepository.save(entity);
        return true;
    }

    // 유저 정보 업데이트
    public void updateUser(UserDto dto) {
        UserEntity entity = UserDto.toEntity(dto);
        userRepository.save(entity);
    }

    // 유저 아이디(String)로 정보 조회
    public UserDto findUser(String userId) {
        UserEntity entity = userRepository.findByIdEquals(userId).orElse(null);
        if (ObjectUtils.isEmpty(entity)) return null;
        return UserDto.fromEntity(entity);
    }

    // 유저 삭제
    public void deleteUser(String userId) {
        UserEntity entity = userRepository.findByIdEquals(userId).orElse(null);
        if (!ObjectUtils.isEmpty(entity)) {
            userRepository.delete(entity);
        }
    }

    // 비밀번호 수정
    public boolean updatePassword(String id, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByIdEquals(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // status 승인
    public boolean approveUserStatus(String id) {
        Optional<UserEntity> userOptional = userRepository.findByIdEquals(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setStatus(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
