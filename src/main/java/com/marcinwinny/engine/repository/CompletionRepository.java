package com.marcinwinny.engine.repository;

import com.marcinwinny.engine.model.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends CrudRepository<Completion, Long>{

    @Query("select c from Completion c where c.completedByUser = ?1")
    Page<Completion> completionsOfUser(String completedByUser, Pageable pageable);

}
