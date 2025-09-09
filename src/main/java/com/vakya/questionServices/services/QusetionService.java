package com.vakya.questionServices.services;

import com.vakya.questionServices.model.Question;
import com.vakya.questionServices.model.QuestionWapper;
import com.vakya.questionServices.model.Response;
import com.vakya.questionServices.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QusetionService {
    private final QuestionRepository questionRepository;

    public QusetionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getQuestion() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public List<Integer> getQuestionForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionByCategory(categoryName,numQuestions);
        return questions;
    }

    public Integer getScore(List<Response> responses) {
        int right=0;
        for(Response response:responses){
            Question question = questionRepository.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return  right;
    }

    public List<QuestionWapper> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWapper> wappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer id : questionIds) {
            questions.add(questionRepository.findById(id));
        }
        for(Question question:questions){
            QuestionWapper wapper = new QuestionWapper();
            wapper.setId((question.getId()));
            wapper.setQuestionTitle(question.getQuestionTitle());
            wapper.setOption1(question.getOption1());
            wapper.setOption2(question.getOption2());
            wapper.setOption3(question.getOption3());
            wapper.setOption4(question.getOption4());

            wappers.add(wapper);
        }


        return wappers;
    }

}
