package com.vakya.questionServices.controller;

import com.vakya.questionServices.model.Question;
import com.vakya.questionServices.model.QuestionWapper;
import com.vakya.questionServices.model.Response;
import com.vakya.questionServices.services.QusetionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    private final QusetionService qusetionService;

    public QuestionController(QusetionService qusetionService) {
        this.qusetionService = qusetionService;
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestBody Question question){
        return qusetionService.addQuestion(question);
    }

    @GetMapping("/allQuestion")
    public List<Question> getQuestion(){
        return qusetionService.getQuestion();
    }

    @GetMapping("/category/{category}")
    public List<Question> getQuestionBYCategory(@PathVariable String category){
        return qusetionService.getQuestionByCategory(category);
    }

    @GetMapping("/generate")
    public List<Integer> getQuestionsForQuiz(@RequestParam String categoryName,
                                             @RequestParam Integer numQuestions){
        return qusetionService.getQuestionForQuiz(categoryName,numQuestions);
    }

    @PostMapping("/getQuestions")
    public List<QuestionWapper> getQuestionFromId(@RequestBody List<Integer> questionIds){
        return qusetionService.getQuestionFromId(questionIds);
    }

    @PostMapping("/getScore")
    public Integer getScore(@RequestBody List<Response> responses){
        return qusetionService.getScore(responses);
    }
}
