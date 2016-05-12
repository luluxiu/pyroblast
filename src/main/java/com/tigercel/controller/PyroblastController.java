package com.tigercel.controller;

import com.tigercel.Bean.PyroblastAuth;
import com.tigercel.Bean.PyroblastLogin;
import com.tigercel.Bean.PyroblastPing;
import com.tigercel.Utils.PyroblastUtils;
import com.tigercel.model.APInfo;
import com.tigercel.model.Connection;
import com.tigercel.model.PyroblastUser;
import com.tigercel.model.support.AuthStatus;
import com.tigercel.service.APService;
import com.tigercel.service.ConnectionService;
import com.tigercel.service.PyroblastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

import static com.tigercel.controller.support.AuthResult.AUTH_ALLOWED;
import static com.tigercel.controller.support.AuthResult.AUTH_DENIED;
import static com.tigercel.controller.support.AuthResult.AUTH_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;



/*
1 not register
    mac
2 recertification
    token
3 roaming
    token
4 success
    token
5 error
*/
/**
 * Created by freedom on 2016/3/30.
 *
 *
 */
@Controller
@RequestMapping("pyroblast")
public class PyroblastController {

    @Autowired
    private PyroblastService pyroblastService;

    @Autowired
    private APService apService;

    @Autowired
    private ConnectionService connectionService;

    private static Logger logger = LoggerFactory.getLogger(PyroblastController.class);

    @RequestMapping(value = "login", method = GET)
    public String index(PyroblastLogin pbl, Model model) {
        APInfo              ap;
        Page<Connection>    conns;
        Connection          currentConn;
        Boolean             roam;
        String              token = PyroblastUtils.generateToken();

        // Check whether the ap is registered based on ap mac
        ap = apService.findByMac(pbl.getGw_id());
        model.addAttribute("pyroblast", pbl);

        if(ap == null) {
            // ap is not registered, return to unregistered information page
            model.addAttribute("code", "1");
            model.addAttribute("msg", pbl.getGw_id());
            return "pyroblast/index";
        }

        // Check whether the user is authenticated based on user mac
        conns = connectionService.findAllByMacAndStatus(pbl.getMac(), AuthStatus.VALIDATED);
        if(conns == null) {
            // user is not authenticated, return to login page

            return "pyroblast/index";
        }

        // Check whether the user is roaming
        currentConn = connectionService.checkRoam(pbl.getGw_id(), conns.getContent());
        if(currentConn != null) {
            // the user is not roaming(previous certification may fail or
            // the user manually enter the authentication page
            // requires recertification)


            connectionService.update(token, currentConn);

            //return to "one-click" page
            model.addAttribute("code", "2");
            model.addAttribute("msg", token);

            return "pyroblast/index";
        }

        // Check whether the ap is turned on roaming function
        roam = ap.getRule().getWander();
        if(roam == false) {
            // ap is turned off roaming function

            return "pyroblast/index";
        }


        connectionService.save(pbl, ap, token);

        //return to "one-click" page
        model.addAttribute("code", "3");
        model.addAttribute("msg", token);

        return "pyroblast/index";   // return to the login page
    }

    @RequestMapping(value = "login", method = POST)
    public String login(PyroblastLogin pbl, Model model) {
        APInfo              ap;
        Page<Connection>    conns;
        Connection          currentConn;
        Boolean             roam;


        //Check whether the ap is registered based on ap mac
        ap = apService.findByMac(pbl.getGw_id());
        //model.addAttribute("pyroblast", pbl);

        if(ap == null) {
            // ap is not registered, return to unregistered information page
            model.addAttribute("code", "1");
            model.addAttribute("msg", pbl.getGw_id());
            return "jsonTemplate";
        }

        //Check whether the user is authenticated based on user mac
        conns = connectionService.findAllByMacAndStatus(pbl.getMac(), AuthStatus.VALIDATED);
        if(conns == null) {
            // user is not authenticated, authenticate the user
            authenticate(pbl, model, ap);

            return "jsonTemplate";
        }

        //Check whether the user is roaming
        currentConn = connectionService.checkRoam(pbl.getGw_id(), conns.getContent());
        if(currentConn != null) {
            // the user is not roaming(previous certification may fail or
            // the user manually enter the authentication page
            // requires recertification)

            String  token = PyroblastUtils.generateToken();
            connectionService.update(token, currentConn);

            //return to "one-click" page
            model.addAttribute("code", "4");
            model.addAttribute("msg", token);

            return "jsonTemplate";
        }

        //Check whether the ap is turned on roaming function
        roam = ap.getRule().getWander();
        if(roam == false) {
            // ap is turned off roaming function

            authenticate(pbl, model, ap);

            return "jsonTemplate";
        }

        String  token = PyroblastUtils.generateToken();
        connectionService.save(pbl, ap, token);
        model.addAttribute("code", "4");
        model.addAttribute("msg", token);

        return "jsonTemplate";
    }

    @RequestMapping(value = "auth", method = GET)
    public String auth(PyroblastAuth pa, Model model) {
        APInfo ap = apService.findByMac(pa.getGw_id());

        logger.debug("====== gw_id - " + pa.getGw_id() + ", mac - " + pa.getMac() +
                    ", token - " + pa.getToken() );

        if(ap == null) {
            model.addAttribute("result", AUTH_ERROR);
            logger.debug("====== gw_id - " + pa.getGw_id() + " is not exist.");
            return "pyroblast/auth";
        }

        switch(pa.getStage()) {
            case "login":
                logger.debug("====== login");
                if(pyroblastService.login(pa, ap) == false) {
                    model.addAttribute("result", AUTH_ERROR);
                }
                else {
                    model.addAttribute("result", AUTH_ALLOWED);
                }
                break;

            case "logout":
                logger.debug("====== logout");
                pyroblastService.logout(pa);
                model.addAttribute("result", AUTH_DENIED);
                break;

            case "counters":
                logger.debug("====== counters");
                if(pyroblastService.counters(pa) == false) {
                    model.addAttribute("result", AUTH_DENIED);
                }
                else {
                    model.addAttribute("result", AUTH_ALLOWED);
                }
                break;

            default:
                logger.debug("====== invalid stage : " + pa.getStage());
                model.addAttribute("result", AUTH_DENIED);
                break;
        }

        return "pyroblast/auth";
    }

    @RequestMapping(value = "ping", method = GET)
    public String ping(PyroblastPing pp, HttpServletRequest request) {
        APInfo ap = apService.findByMac(pp.getGw_id());

        if(ap == null) {
            return "pyroblast/pong";
        }

        apService.save(ap, pp, request);

        /* pass through */
        return "pyroblast/pong";
    }

    @RequestMapping(value = "message", method = GET)
    public String message() {

        return "pyroblast/message";
    }

    @RequestMapping(value = "portal", method = GET)
    public String portal(@RequestParam(value = "gw_id", required = true) String mac,
                         Model model) {
        APInfo ap = apService.findByMac(mac);

        if(ap == null) {
            model.addAttribute("code", "1");
            model.addAttribute("msg", mac);
            return "pyroblast/index";
        }

        return "pyroblast/portal";
    }

    private void authenticate(PyroblastLogin pbl, Model model, APInfo ap) {
        PyroblastUser       user;
        String              token = PyroblastUtils.generateToken();

        user = pyroblastService.check(pbl.getUsername(), pbl.getPassword(), false);

        if(user == null) {
            model.addAttribute("code", "5");
        }
        else {
            connectionService.save(pbl, ap, token);

            model.addAttribute("code", "4");
            model.addAttribute("msg", token);
        }
    }

}
