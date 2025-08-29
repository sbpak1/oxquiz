package com.example.OXQuiz.controller;

import com.example.OXQuiz.dto.PlayDto;
import com.example.OXQuiz.dto.QuizDto;
import com.example.OXQuiz.entity.QuizEntity;
import com.example.OXQuiz.service.PlayService;
import com.example.OXQuiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final PlayService playService;

    @GetMapping("list")
    public String getQuizList(Model model) {
        List<QuizEntity> quizList = quizService.findAllQuizzes();
        model.addAttribute("quizList", quizList);
        return "quiz/list";
    }

    @GetMapping("insert")
    public String getInsertQuiz(Model model) {
        model.addAttribute("quizDto", new QuizDto());
        return "quiz/insert";
    }

    @PostMapping("insert")
    public String postInsertQuiz(@ModelAttribute("quizDto") QuizDto dto) {
        quizService.saveQuiz(dto);
        return "redirect:/quiz/list";
    }

    @GetMapping("update/{id}")
    public String getUpdateQuiz(@PathVariable("id") Integer id, Model model) {
        QuizEntity quizEntity = quizService.findQuizById(id);
        if (quizEntity == null) return "redirect:/quiz/list";
        model.addAttribute("quizDto", QuizDto.fromEntity(quizEntity));
        return "quiz/update";
    }

    @PostMapping("update")
    public String postUpdateQuiz(@ModelAttribute("quizDto") QuizDto dto) {
        quizService.saveQuiz(dto);
        return "redirect:/quiz/list";
    }

    @PostMapping("delete/{id}")
    public String postDeleteQuiz(@PathVariable("id") Integer id) {
        quizService.deleteQuizById(id);
        return "redirect:/quiz/list";
    }

    @GetMapping("play")
    public String getPlayQuiz(Model model) {
        QuizEntity randomQuiz = quizService.findRandomQuiz();
        if (randomQuiz == null) return "redirect:/quiz/list?error=noquiz";
        model.addAttribute("quiz", randomQuiz);
        return "quiz/play";
    }

    @PostMapping("check")
    public String postCheckQuiz(@RequestParam("quizid") Integer quizId,
                                @RequestParam("answer") boolean userAnswer,
                                Model model) {
        QuizEntity quiz = quizService.findQuizById(quizId);
        if (quiz == null) {
            return "redirect:/quiz/list?error=notfound";
        }

        boolean isCorrect = quiz.getAnswer() == userAnswer;

        PlayDto playDto = new PlayDto();
        playDto.setQuizId(Integer.valueOf(quizId));
        playDto.setIsCorrect(isCorrect);
        // playService.savePlayRecord(playDto); // 필요 시 기록 저장

        model.addAttribute("isCorrect", isCorrect);
        return "quiz/result";
    }
}
