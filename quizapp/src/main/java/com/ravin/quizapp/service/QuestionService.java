package com.ravin.quizapp.service;

import com.ravin.quizapp.dao.QuestionDao;
import com.ravin.quizapp.exception.QuestionNotFoundException;
import com.ravin.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }

    public Question getQuestionById(Integer id) throws QuestionNotFoundException {
        Optional<Question> optionalQuestion = questionDao.findById(id);
        if(optionalQuestion.isPresent()){
            return optionalQuestion.get();
        }
        throw new QuestionNotFoundException("Question Not Found");
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public String saveQuestion(Question question) {
        questionDao.save(question);
        return "Successfully saved";
    }

    public String deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return "Deleted successfully";
    }
}
