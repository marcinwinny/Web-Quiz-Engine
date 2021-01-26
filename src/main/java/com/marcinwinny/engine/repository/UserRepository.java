package com.marcinwinny.engine.repository;

import com.marcinwinny.engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u FROM User u ORDER BY id")
    Page<User> findAllUserWithPagination(Pageable pageable);

}
