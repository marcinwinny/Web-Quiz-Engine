package com.marcinwinny.engine.repository;

import com.marcinwinny.engine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

    @Query(
            value = "SELECT * FROM Quizzes ORDER BY id",
            countQuery = "SELECT count(*) FROM Quizzes",
            nativeQuery = true)
    Page<Quiz> findAllQuizzesWithPagination(Pageable pageable);

}