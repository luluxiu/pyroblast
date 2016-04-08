package com.tigercel.repository;

import com.tigercel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/4/1.
 */
@Repository
@Transactional
public interface PyroblastRepository extends JpaRepository<User, Long> {

    User findByName(String username);
}
