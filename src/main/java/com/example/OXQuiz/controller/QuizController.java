package com.example.OXQuiz.controller;

import com.example.OXQuiz.dto.PlayDto;
import com.example.OXQuiz.dto.QuizDto;
import com.example.OXQuiz.entity.QuizEntity;
import com.example.OXQuiz.service.PlayService;
import com.example.OXQuiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final PlayService playService;

    // 퀴즈 목록 페이지
    @GetMapping("list")
    public String getQuizList(Model model) {
        List<QuizEntity> quizList = quizService.findAllQuizzes();
        model.addAttribute("quizList", quizList);
        return "/quiz/list";
    }

    // 퀴즈 등록 폼
    @GetMapping("insert")
    public String getInsertQuiz(Model model) {
        model.addAttribute("quizDto", new QuizDto());
        return "/quiz/insert";
    }

    // 퀴즈 등록 후 목록 페이지로 리다이렉트
    @PostMapping("insert")
    public String postInsertQuiz(@ModelAttribute("quizDto") QuizDto dto) {
        quizService.insertQuiz(dto);
        return "redirect:/quiz/list";
    }

    // 퀴즈 수정 폼
    @GetMapping("update/{id}")
    public String getUpdateQuiz(@PathVariable("id") int id, Model model) {
        QuizEntity quiz = quizService.findQuizById(id);
                model.addAttribute("quizDto", quiz);
        return "/quiz/update";
    }

    @PostMapping("update")
    public String postUpdateQuiz(@ModelAttribute("quizDto") QuizDto dto) {
        quizService.updateQuiz(dto);
        return "redirect:/quiz/list";
    }

    // 퀴즈 삭제 후 목록 페이지로 리다이렉트
    @PostMapping("delete/{id}")
    public String postDeleteQuiz(@PathVariable("id") int id) {
        quizService.deleteQuizById(id);
        return "redirect:/quiz/list";
    }

    // 퀴즈 게임 페이지를 보여주고 랜덤 퀴즈 제공
    @GetMapping("play")
    public String getPlayQuiz(Model model) {
        QuizEntity randomQuiz = quizService.findRandomQuiz();
        model.addAttribute("quiz", randomQuiz);
        return "/quiz/play";
    }

    // 퀴즈 답안 체크후 결과 처리
    @PostMapping("check")
    public String postCheckQuiz(@ModelAttribute("playDto") PlayDto dto,
                                @RequestParam("quizid") int quizId,
                                HttpSession session, Model model) {
        QuizEntity quiz = quizService.findQuizById(quizId);
        boolean isCorrect = quiz.isAnswer() == dto.isCorrect();

        dto.setCorrect(isCorrect);
        playService.savePlayRecord(dto);

        model.addAttribute("isCorrect", isCorrect);
        return "/quiz/result";
    }


}
