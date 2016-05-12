package com.tigercel.controller;

import com.tigercel.Bean.PageBean;
import com.tigercel.Utils.DTOUtil;
import com.tigercel.model.PyroblastUser;
import com.tigercel.service.PyroUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/4/11.
 */
@Controller
@RequestMapping("pyrouser")
public class PyroUserController {

    @Autowired
    private PyroUserService pyroUserService;

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        model.addAttribute("menu", "users");

        return "pyrouser/index";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {
        Page<PyroblastUser> users = null;

        if(search != null && search.length() != 0) {
            users = pyroUserService.findAllByUsername(search, pb);
        }
        else {
            users = pyroUserService.findAll(pb);
        }

        model.addAttribute("total", users.getTotalElements());
        model.addAttribute("rows", users.getContent());

        return "jsonTemplate";
    }


    @RequestMapping(value = "new", method = POST)
    public String add(@RequestParam(value = "username", required = true) String username,
                    @RequestParam(value = "password", required = true) String password,
                    @RequestParam(value = "description", required = false) String description,
                    Model model) {

        PyroblastUser user = pyroUserService.findOneByUsername(username);

        if(user != null) {
            model.addAttribute("error", "用户名已存在");
            return "msg/error";
        }
        user = new PyroblastUser();

        user.setUsername(username);
        user.setPassword(password);
        user.setDescription(description);
        pyroUserService.save(user);
        return "msg/success";

    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(@RequestParam(value = "id", required = true) Long id,
                       @RequestParam(value = "username", required = true) String username,
                       @RequestParam(value = "password", required = true) String password,
                       @RequestParam(value = "description", required = false) String description,
                       Model model
                       ) {
        PyroblastUser user = null;

        if(id != null && id > 0) {
            user = pyroUserService.findOne(id);
            if(user != null) {
                user.setUsername(username);
                user.setPassword(password);
                user.setDescription(description);
                pyroUserService.save(user);
                return "msg/success";
            }
            else {
                model.addAttribute("error", "User is not exist.");
                return "msg/error";
            }
        }
        else {
            model.addAttribute("error", "User id is not valid.");
            return "msg/error";
        }
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(@RequestParam("id") Long id) {

        if(id != null && id > 0) {
            pyroUserService.delete(id);
        }

        return "msg/success";
    }
}
