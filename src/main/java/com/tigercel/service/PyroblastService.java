package com.tigercel.service;

import com.tigercel.Utils.PyroblastUtils;
import com.tigercel.model.User;
import com.tigercel.repository.PyroblastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by freedom on 2016/4/1.
 */
@Service
public class PyroblastService {

    @Autowired
    private PyroblastRepository pyroblastRepository;

    public User check(String username, String password) {
        User    user;
        String  hashString;

        user = pyroblastRepository.findByName(username);
        if(user == null) {
            return null;
        }

        hashString = PyroblastUtils.MD5(password);
        if(hashString != null && hashString.equalsIgnoreCase(user.getPassword())) {
            return user;
        }
        else {
            return null;
        }
    }
}
