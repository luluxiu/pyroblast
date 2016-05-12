package com.tigercel.repository;

import com.tigercel.model.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/3/29.
 */
@Repository
@Transactional
public interface RuleRepository extends JpaRepository<Rule, Long> {

    Rule findByName(String name);
    Page<Rule> findAllByName(String name, Pageable pageable);
}
