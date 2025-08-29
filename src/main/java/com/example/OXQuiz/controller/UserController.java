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

    // 유저 목록 페이지 (관리자용)
    @GetMapping("list")
    public String getUserList(Model model) {
        List<UserDto> userDtoList = userService.findAllUsers();
        model.addAttribute("userList", userDtoList);
        return "/user/list";
    }

    // 회원가입 페이지
    @GetMapping("signup")
    public String getSignupUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/user/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String postSignupUser(@Valid @ModelAttribute("userDto") UserDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/user/signup";

        boolean success = userService.insertUser(dto);
        if (!success) {
            bindingResult.rejectValue("id", "error.id", "이미 존재하는 아이디입니다.");
            return "/user/signup";
        }

        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("login")
    public String getLogin(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute("userDto") UserDto dto,
                            BindingResult bindingResult,
                            HttpSession session) {
        UserDto loginResult = userService.findUser(dto.getId());

        if (loginResult == null) {
            bindingResult.rejectValue("id", "error.id", "아이디가 존재하지 않습니다.");
            return "/user/login";
        }

        if (!loginResult.getPassword().equals(dto.getPassword())) {
            bindingResult.rejectValue("password", "error.password", "비밀번호가 일치하지 않습니다.");
            return "/user/login";
        }

        if (!loginResult.isAdmin() && !loginResult.isStatus()) {
            bindingResult.rejectValue("id", "error.status", "관리자 승인이 필요합니다.");
            return "/user/login";
        }

        session.setAttribute("loggedInUser", loginResult);
        return "redirect:/";
    }

    // 로그아웃 처리
    @GetMapping("logout")
    public String userLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @PostMapping("update")
    public String getUserUpdate(@RequestParam("id") String id, Model model) {
        UserDto userDto = userService.findUser(id);
        model.addAttribute("userDto", userDto);
        return "/user/update";
    }

    @PostMapping("updateUser")
    public String postUpdateUser(@Valid @ModelAttribute("userDto") UserDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/user/update";
        userService.updateUser(dto);
        return "redirect:/user/list";
    }

    @PostMapping("approve/{id}")
    public String approveUserStatus(@PathVariable("id") String id) {
        boolean success = userService.approveUserStatus(id);
        // 승인 후 사용자 목록 페이지로 리다이렉트
        return "redirect:/user/list";
    }

}
