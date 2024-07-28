package com.ravin.quizapp.service;

import com.ravin.quizapp.dao.QuestionDao;
import com.ravin.quizapp.dao.QuizDao;
import com.ravin.quizapp.model.Question;
import com.ravin.quizapp.model.QuestionWrapper;
import com.ravin.quizapp.model.Quiz;
import com.ravin.quizapp.model.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        //Generating Questions randomly using Question Dao Custom method
        List<Question> questionList = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = Quiz.builder()
                .title(title)
                .questions(questionList)
                .build();

        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        QuestionWrapper qw;
        for(Question q: questions){
            qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            );
            questionWrappers.add(qw);
        }
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<String> submitQuiz(Integer id, List<QuizResponse> quizResponses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int i=0, score=0;
        for(Question q: questions){
            if(q.getRightAnswer().equals(quizResponses.get(i).getResponse()))
                score++;
            i++;
        }
        return new ResponseEntity<>("Your score is "+score, HttpStatus.OK);
    }
}
