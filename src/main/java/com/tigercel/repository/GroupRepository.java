package com.tigercel.repository;

import com.tigercel.model.Group;
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
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String name);
    Page<Group> findAllByName(String name, Pageable pageable);
}
