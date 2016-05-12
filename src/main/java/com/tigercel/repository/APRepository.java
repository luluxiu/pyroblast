package com.tigercel.repository;

import com.tigercel.model.APInfo;
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
public interface APRepository extends JpaRepository<APInfo, Long> {

    APInfo findByName(String name);
    APInfo findByMac(String mac);
    Page<APInfo> findAllByName(String name, Pageable pageable);
    Page<APInfo> findAllByGroup(Group group, Pageable pageable);
    Page<APInfo> findAllByMac(String mac, Pageable pageable);
}
