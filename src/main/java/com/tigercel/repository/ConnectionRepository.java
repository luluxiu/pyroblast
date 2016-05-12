package com.tigercel.repository;

import com.tigercel.model.APInfo;
import com.tigercel.model.Connection;
import com.tigercel.model.support.AuthStatus;
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
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Page<Connection> findAllByAp(APInfo ap, Pageable pageRequest);

    Page<Connection> findAllByMac(String mac, Pageable pageRequest);

    Page<Connection> findAllByMacAndStatus(String mac, AuthStatus status, Pageable pageRequest);

    Connection findOneByMacAndAp(String mac, APInfo ap);

    //Connection findOneByToken(String token);
    Connection findOneByMacAndApAndToken(String mac, APInfo ap, String token);
}
