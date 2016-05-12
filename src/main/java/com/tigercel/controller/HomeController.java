package com.tigercel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/3/30.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "", method = GET)
    public String index() {

        return "login";
    }

    @RequestMapping(value="/login", method = GET)
    public String login(Model model,
                        @RequestParam(value="error", required = false) String error,
                        @RequestParam(value="logout", required = false) String logout) {
        if(error != null) {
            model.addAttribute("msg", "error");
        }

        if(logout != null) {
            model.addAttribute("msg", "logout");
        }

        return "/login";
    }

    @RequestMapping(value="/logout", method = GET)
    public String logout(Model model) {


        return "/login";
    }
}
