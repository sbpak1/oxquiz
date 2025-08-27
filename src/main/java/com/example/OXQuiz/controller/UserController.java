package com.example.OXQuiz.controller;

import com.example.OXQuiz.dto.UserDto;
import com.example.OXQuiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 목록 페이지
    @GetMapping("list")
    public String getUserList(Model model) {
        List<UserDto> userDtoList = userService.findAllUsers();
        model.addAttribute("userList", userDtoList);
        return "/user/list";
    }

    // 회원가입 폼
    @GetMapping("signup")
    public String getSignupUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/user/signup";
    }

    // 회원가입 처리후 로그인 페이지로 리다이렉트
    @PostMapping("signup")
    public String postSignupUser(@Valid @ModelAttribute("userDto") UserDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/signup";
        }
        userService.insertUser(dto);
        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("login")
    public String getLogin(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/user/login";
    }

    // 로그인 데이터 처리
    // 성공 시 메인 페이지, 실패 시 로그인 페이지로 이동
    @PostMapping("login")
    public String postLogin(@Valid @ModelAttribute("userDto") UserDto dto,
                            BindingResult bindingResult,
                            HttpSession session) {
        UserDto loginResult = userService.findUser(dto.getId());
        if (ObjectUtils.isEmpty(loginResult)) {
            bindingResult.rejectValue("id", "error.id", "아이디가 존재하지 않습니다.");
            return "/user/login";
        } else if (loginResult.getPassword().equals(dto.getPassword())) {
            if (loginResult.isAdmin() || loginResult.isStatus()) {
                session.setAttribute("loggedInUser", loginResult);
                return "redirect:/";
            } else {
                bindingResult.rejectValue("id", "error.status", "관리자 승인이 필요합니다.");
                return "/user/login";
            }
        } else {
            bindingResult.rejectValue("password", "error.password", "비밀번호가 일치하지 않습니다.");
            return "/user/login";
        }
    }

    // 로그아웃 처리
    @GetMapping("logout")
    public String userLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    // 유저 정보 수정 페이지
    @PostMapping("update")
    public String getUserUpdate(@RequestParam("id") String id, Model model) {
        UserDto userDto = userService.findUser(id);
        model.addAttribute("userDto", userDto);
        return "/user/update";
    }
    
    // 유저 정보 업데이트 처리
    @PostMapping("updateUser")
    public String postUpdateUser(@Valid @ModelAttribute("userDto") UserDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/update";
        }
        userService.updateUser(dto);
        return "redirect:/user/list";
    }

}
