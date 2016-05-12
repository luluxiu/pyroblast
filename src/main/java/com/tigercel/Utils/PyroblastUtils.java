package com.tigercel.Utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by freedom on 2016/4/8.
 */
public class PyroblastUtils {

    public static String MD5(String str) {
        MessageDigest   md5     = null;
        byte[]          digest;
        String          hashString ;


        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        digest = md5.digest(str.getBytes());
        hashString = new BigInteger(1, digest).toString(16);

        return hashString;
    }

    public static String generateToken() {

        return UUID.randomUUID().toString();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");

        if(!StringUtils.isBlank(ip) && !ip.equalsIgnoreCase("unknown")) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if(!StringUtils.isBlank(ip) && !ip.equalsIgnoreCase("unknown")) {
            int index = ip.indexOf(',');
            if(index != -1) {
                return ip.substring(0, index);
            }
            else {
                return ip;
            }
        }
        else {
            return request.getRemoteAddr();
        }
    }

}
