package com.tigercel.service;

import com.tigercel.Bean.PyroblastAuth;
import com.tigercel.Constants;
import com.tigercel.Utils.PyroblastUtils;
import com.tigercel.model.APInfo;
import com.tigercel.model.Connection;
import com.tigercel.model.PyroblastUser;
import com.tigercel.model.support.AuthStatus;
import com.tigercel.repository.PyroblastRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Created by freedom on 2016/4/1.
 */
@Service
public class PyroblastService {

    @Autowired
    private PyroblastRepository pyroblastRepository;

    @Autowired
    private AppService appService;

    @Autowired
    private ConnectionService connectionService;

    private static Logger logger = LoggerFactory.getLogger(PyroblastService.class);

    @Autowired
    private APService apService;

    public PyroblastUser check(String username, String password, Boolean type) {
        PyroblastUser       user;
        String              hashString;

        user = pyroblastRepository.findByUsername(username);
        if(user == null) {
            return null;
        }

        if(type) {  // md5
            hashString = PyroblastUtils.MD5(password);
        }
        else {      // the password is already enconded by md5
            hashString = password;
        }

        if(hashString != null && hashString.equals(user.getPassword())) {
            return user;
        }
        else {
            return null;
        }
    }

    public boolean login(PyroblastAuth pa, APInfo ap) {
        String      token = null;
        Connection  conn = null;

        if(pa.getClient().equals(Constants.CONNECTION_CLIENT_APP)) {
            logger.debug("====== request from app");
            token = appService.search(pa.getToken());

            if(token == null) {
                logger.debug("can not find token - " + pa.getToken());
                return false;
            }

            connectionService.save(pa, ap, AuthStatus.VALIDATED);
        }
        else {
            logger.debug("====== request from browser");
            conn = connectionService.findOneByMacAndApAndToken(
                        pa.getMac(),
                        apService.findByMac(pa.getGw_id()),
                        pa.getToken());

            if(conn == null) {
                logger.debug("====== can not find conn");
                return false;
            }

            connectionService.save(pa, conn, AuthStatus.VALIDATED);
        }

        return true;
    }

    public boolean logout(PyroblastAuth pa) {
        Connection  conn = null;

        conn = connectionService.findOneByMacAndApAndToken(
                pa.getMac(),
                apService.findByMac(pa.getGw_id()),
                pa.getToken());

        if(conn == null) {
            return false;
        }

        connectionService.save(pa, conn, AuthStatus.LOGOUT);

        return true;
    }

    public boolean counters(PyroblastAuth pa) {
        Connection  conn = null;

        conn = connectionService.findOneByMacAndApAndToken(
                pa.getMac(),
                apService.findByMac(pa.getGw_id()),
                pa.getToken());

        if(conn == null) {
            return false;
        }

        conn.setIp(pa.getIp());
        conn.setOutgoing(pa.getOutgoing());
        conn.setIncoming(pa.getIncoming());

        connectionService.save(conn);

        return true;
    }

}
