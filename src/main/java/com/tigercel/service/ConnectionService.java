package com.tigercel.service;

import com.tigercel.Bean.PageBean;
import com.tigercel.Bean.PyroblastAuth;
import com.tigercel.Bean.PyroblastLogin;
import com.tigercel.model.APInfo;
import com.tigercel.model.Connection;
import com.tigercel.model.support.AuthStatus;
import com.tigercel.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by freedom on 2016/3/30.
 */
@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    public Page<Connection> findAllByAP(APInfo ap, PageBean pb) {

        return connectionRepository.findAllByAp(
                ap,
                new PageRequest(pb.getPage() - 1,
                                pb.getSize(),
                                Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                                pb.getSortName()
                )
        );
    }

    public Page<Connection> findAllByMac(String mac, PageBean pb) {

        return connectionRepository.findAllByMac(
                mac,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    /*
    public Connection findOneByToken(String token) {
        return connectionRepository.findOneByToken(token);
    }
    */

    public Page<Connection> findAllByMacAndStatus(String mac, AuthStatus as) {

        Page<Connection> conns;

        conns = connectionRepository.findAllByMacAndStatus(mac, as, new PageRequest(0, 1));
        if(conns == null || conns.getContent().isEmpty()) {
            return null;
        }

        return conns;
    }

    public Connection findOneByMacAndApAndToken(String mac, APInfo ap, String token) {
        return connectionRepository.findOneByMacAndApAndToken(mac, ap, token);
    }

    public Connection checkRoam(String apId, List<Connection> conns) {

        for(Connection c : conns) {
            if(apId.equalsIgnoreCase(c.getAp().getMac())) {
                return c;
            }
        }

        return null;
    }

    public Connection update(String token, Connection conn) {

        conn.setStatus(AuthStatus.VALIDATING);
        conn.setToken(token);

        return connectionRepository.save(conn);
    }

    public Connection save(PyroblastLogin pbl, APInfo ap, String token) {

        Connection conn = null;

        conn = connectionRepository.findOneByMacAndAp(pbl.getMac(), ap);
        if(conn == null) {
            conn = new Connection();
            conn.setMac(pbl.getMac());
            conn.setAp(ap);
        }

        conn.setStatus(AuthStatus.VALIDATING);
        conn.setToken(token);

        return connectionRepository.save(conn);
    }

    public Connection save(PyroblastAuth pa, APInfo ap, AuthStatus as) {
        Connection conn = null;

        conn = connectionRepository.findOneByMacAndAp(pa.getMac(), ap);
        if(conn == null) {
            conn = new Connection();
            conn.setMac(pa.getMac());
            conn.setAp(ap);
        }

        if(as == AuthStatus.VALIDATED) {
            conn.setLoginCount(conn.getLoginCount() + 1);
            conn.setLoginTime(new Date());
        }

        conn.setIp(pa.getIp());
        conn.setToken(pa.getToken());
        conn.setStatus(as);
        conn.setClient(pa.getClient());
        conn.setIncoming(pa.getIncoming());
        conn.setOutgoing(pa.getOutgoing());

        return connectionRepository.save(conn);
    }

    public Connection save(PyroblastAuth pa, Connection conn, AuthStatus as) {


        if(as == AuthStatus.VALIDATED) {
            conn.setLoginCount(conn.getLoginCount() + 1);
            conn.setLoginTime(new Date());
        }

        conn.setIp(pa.getIp());
        conn.setToken(pa.getToken());
        conn.setStatus(as);
        conn.setClient(pa.getClient());
        conn.setIncoming(pa.getIncoming());
        conn.setOutgoing(pa.getOutgoing());

        return connectionRepository.save(conn);
    }

    public Connection save(Connection conn) {


        return connectionRepository.save(conn);
    }

    public Page<Connection> findAll(PageBean pb) {
        return connectionRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                    pb.getSize(),
                    Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                    pb.getSortName())
        );
    }

}
