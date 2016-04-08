package com.tigercel.controller;

import com.tigercel.Bean.GroupBean;
import com.tigercel.Bean.PageBean;
import com.tigercel.Utils.DTOUtil;
import com.tigercel.model.Group;
import com.tigercel.service.GroupService;
import com.tigercel.service.RuleService;
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
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        return "group/index";
    }

    @RequestMapping(value = "new", method = POST)
    public String add(GroupBean gb) {

        Group group = groupService.findByName(gb.getName());

        if(group != null) {
            return "msg/error";
        }
        group = DTOUtil.map(gb, Group.class);

        if(gb.getRuleId() > 0) {
            groupService.save(group, ruleService.findOne(gb.getRuleId()));
        }
        else {
            groupService.save(group);
        }


        return "msg/success";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(GroupBean gb) {

        Group group = groupService.findOne(gb.getId());

        DTOUtil.mapTo(gb, group);
        if(gb.getRuleId() > 0) {
            groupService.save(group, ruleService.findOne(gb.getRuleId()));
        }
        else {
            groupService.save(group);
        }


        return "msg/success";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(GroupBean gb) {

        groupService.delete(gb.getId());

        return "msg/success";
    }


    @RequestMapping(value = "show", method = GET)
    public String show(PageBean pb, Model model) {

        Page<Group> groups = groupService.findAll(pb);

        model.addAttribute("total", groups.getTotalElements());
        model.addAttribute("rows", groups.getContent());

        return "jsonTemplate";
    }
}
