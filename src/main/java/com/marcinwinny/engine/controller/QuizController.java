package com.marcinwinny.engine.controller;


import com.marcinwinny.engine.model.Answer;
import com.marcinwinny.engine.model.AnswerResponse;
import com.marcinwinny.engine.model.Completion;
import com.marcinwinny.engine.model.Quiz;
import com.marcinwinny.engine.repository.CompletionRepository;
import com.marcinwinny.engine.repository.QuizRepository;
import com.marcinwinny.engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

//@Validated
@RestController
@RequestMapping(path = "/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CompletionRepository completionRepository;

    @Autowired
    private QuizService quizService;

    @GetMapping
    public Page<Quiz> findAllQuizzesWithPages(@RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(defaultValue = "id") String sortBy){
        return quizRepository.findAllQuizzesWithPagination(
                PageRequest.of(page,size, Sort.Direction.ASC, sortBy));
    }

    //get all completions of quizzes with paging
    @GetMapping(path = "completed")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<Completion>> findAllCompletionsWithPages(@RequestParam(defaultValue = "0") Integer page,
                                                                        @RequestParam(defaultValue = "10") Integer size,
                                                                        @RequestParam(defaultValue = "completedAt") String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, sortBy);
        Page<Completion> pagedResult = completionRepository.completionsOfUser(
                SecurityContextHolder.getContext().getAuthentication().getName(), pageable);

        return new ResponseEntity<>(pagedResult, new HttpHeaders(), HttpStatus.OK);
    }

    //add new quiz
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Quiz postQuiz(@Valid @RequestBody Quiz quiz){
        quiz.setCreatedByUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return quizRepository.save(quiz);
    }

    //get quiz by id
    @GetMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Quiz getQuestion(@PathVariable long id) {

        Optional<Quiz> quiz = quizRepository.findById(id);

        quiz.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        return quiz.get();

    }

    //solving a quiz
    @PostMapping(path = "{id}/solve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public AnswerResponse solveQuiz(@PathVariable long id, @RequestBody Answer answer){
        if(answer == null){
            answer.setAnswer(new int[0]);
        }

        Optional<Quiz> quizOptional = quizRepository.findById(id);

        quizOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        Quiz quiz = quizOptional.get();

        if(quiz.getAnswer() == null){
            quiz.setAnswer(new int[0]);
        }

        int [] arr1 = answer.getAnswerArr();
        int [] arr2 = quiz.getAnswer();
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        if(Arrays.equals(arr1, arr2)){

            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            Completion completion = new Completion(quiz.getId(), user);
            completionRepository.save(completion);
            return AnswerResponse.CORRECT_ANSWER;
        }
        else{
            return AnswerResponse.WRONG_ANSWER;
        }
    }

    //delete quiz by id, only by user that created it
    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity deleteQuizById(@PathVariable long id){
        boolean isQuizPresent = quizRepository.findById(id).isPresent();

        if(isQuizPresent){
            boolean isCreatedByUser = quizRepository.findById(id).get().getCreatedByUser()
                    .equals(SecurityContextHolder.getContext().getAuthentication().getName());
            if(isCreatedByUser){
                quizRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete all quizzes
    @DeleteMapping(path = "deleteAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAllQuizzes(){
        quizRepository.deleteAll();
    }

}