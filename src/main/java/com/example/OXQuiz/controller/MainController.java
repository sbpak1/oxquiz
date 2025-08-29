package com.example.OXQuiz.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        // 로그인 사용자 정보 가져오기
        Object loggedInUser = session.getAttribute("loggedInUser");
        model.addAttribute("user", loggedInUser);

        return "main";
    }
}
