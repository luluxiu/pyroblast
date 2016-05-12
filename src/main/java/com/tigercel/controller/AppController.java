package com.tigercel.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tigercel.Utils.PyroblastUtils;
import com.tigercel.model.PyroblastUser;
import com.tigercel.service.AppService;
import com.tigercel.service.PyroblastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/4/11.
 */
@Controller
@RequestMapping("app")
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private PyroblastService pyroblastService;

    @RequestMapping(value = "login", method = POST)
    public String login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            Model model) {

        PyroblastUser       user;


        user = pyroblastService.check(username, password, false);

        if(user == null) {
            model.addAttribute("result", "error");
            model.addAttribute("token", "");
        }
        else {
            String token = PyroblastUtils.generateToken();

            appService.save(token);

            model.addAttribute("result", "success");
            model.addAttribute("token", token);
        }

        return "jsonTemplate";
    }
}
