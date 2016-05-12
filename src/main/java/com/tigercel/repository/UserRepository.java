package com.tigercel.repository;

import com.tigercel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/3/29.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
