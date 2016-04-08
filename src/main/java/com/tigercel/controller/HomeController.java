package com.tigercel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/3/30.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {
        return "login";
    }
}
