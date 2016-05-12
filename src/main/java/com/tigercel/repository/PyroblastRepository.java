package com.tigercel.repository;

import com.tigercel.model.PyroblastUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/4/1.
 */
@Repository
@Transactional
public interface PyroblastRepository extends JpaRepository<PyroblastUser, Long> {

    PyroblastUser findByUsername(String username);
    Page<PyroblastUser> findAllByUsername(String name, Pageable pageable);
}
