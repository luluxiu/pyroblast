package com.tigercel.controller;

import com.tigercel.Bean.PyroblastLogin;
import com.tigercel.Bean.PyroblastPing;
import com.tigercel.Utils.PyroblastUtils;
import com.tigercel.model.APInfo;
import com.tigercel.model.Connection;
import com.tigercel.model.User;
import com.tigercel.model.support.AuthStatus;
import com.tigercel.service.APService;
import com.tigercel.service.ConnectionService;
import com.tigercel.service.PyroblastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by freedom on 2016/3/30.
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

            return "redirect:pyroblast/unregistered";
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
            model.addAttribute("token", token);

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
        model.addAttribute("token", token);

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
        model.addAttribute("pyroblast", pbl);

        if(ap == null) {
            // ap is not registered, return to unregistered information page

            return "redirect:pyroblast/unregistered";
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
            model.addAttribute("result", "success");
            model.addAttribute("token", token);

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
        model.addAttribute("result", "success");
        model.addAttribute("token", token);

        return "jsonTemplate";
    }

    @RequestMapping(value = "auth", method = GET)
    public String auth(Model model) {

        return "pyroblast/auth";
    }

    @RequestMapping(value = "ping", method = GET)
    public String ping(PyroblastPing pp) {

        /* pass through */
        return "pyroblast/pong";
    }


    private void authenticate(PyroblastLogin pbl, Model model, APInfo ap) {
        User                user;
        String              token = PyroblastUtils.generateToken();

        user = pyroblastService.check(pbl.getUsername(), pbl.getPassword());

        if(user == null) {
            model.addAttribute("result", "error");
        }
        else {
            connectionService.save(pbl, ap, token);

            model.addAttribute("result", "success");
            model.addAttribute("token", token);
        }
    }

}
