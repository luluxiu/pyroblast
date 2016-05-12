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
import org.springframework.web.bind.annotation.RequestParam;

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

        model.addAttribute("menu", "groups");
        model.addAttribute("title", "分组列表");
        model.addAttribute("rules", ruleService.findAll());

        return "group/index";
    }

    @RequestMapping(value = "new", method = POST)
    public String add(GroupBean gb, Model model) {

        Group group = groupService.findByName(gb.getName());

        if(group != null) {
            model.addAttribute("error", "分组名已存在");
            return "msg/error";
        }
        group = DTOUtil.map(gb, Group.class);

        if(gb.getRuleName() != null) {
            groupService.save(group, ruleService.findByName(gb.getRuleName()));
        }
        else {
            groupService.save(group);
        }

        return "msg/success";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(GroupBean gb) {
        Group group = null;

        if(gb.getId() > 0) {
            group = groupService.findOne(gb.getId());

            DTOUtil.mapTo(gb, group);
            if(gb.getRuleName() != null) {
                groupService.save(group, ruleService.findByName(gb.getRuleName()));
            }
            else {
                groupService.save(group);
            }
        }

        return "msg/success";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(GroupBean gb) {

        if(gb.getId() > 0) {
            groupService.delete(gb.getId());
        }

        return "msg/success";
    }


    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {

        Page<Group> groups = null;

        if(search != null && search.length() != 0) {
            groups = groupService.findAllByName(pb, search);
        }
        else {
            groups = groupService.findAll(pb);
        }

        for(Group group : groups.getContent()) {
            if(group.getRule() != null) {
                group.setRuleName(group.getRule().getName());
            }
        }

        model.addAttribute("total", groups.getTotalElements());
        model.addAttribute("rows", groups.getContent());

        return "jsonTemplate";
    }
}
