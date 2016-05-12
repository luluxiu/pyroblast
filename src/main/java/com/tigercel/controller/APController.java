package com.tigercel.controller;

import com.tigercel.Bean.APBean;
import com.tigercel.Bean.PageBean;
import com.tigercel.Utils.DTOUtil;
import com.tigercel.model.APInfo;
import com.tigercel.model.Group;
import com.tigercel.model.Rule;
import com.tigercel.service.APService;
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
@RequestMapping("ap")
public class APController {

    @Autowired
    private APService apService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "", method = GET)
    public String index(@RequestParam(value = "groupName", required = false) String groupName,
                        Model model) {

        model.addAttribute("menu", "devices");
        if(groupName != null) {
            model.addAttribute("title", groupName + "分组的AP");
            model.addAttribute("group", groupName);
        }
        else {
            model.addAttribute("title", "AP 列表");
        }
        model.addAttribute("rules", ruleService.findAll());

        return "ap/index";
    }


    @RequestMapping(value = "new", method = POST)
    public String add(APBean ab, Model model) {

        APInfo ap = apService.findByName(ab.getName());

        if(ap != null) {
            model.addAttribute("error", "AP 名已存在");
            return "msg/error";
        }
        ap = DTOUtil.map(ab, APInfo.class);

        if(ab.getGroupName() != null) {
            Group group = groupService.findByName(ab.getGroupName());
            Rule rule = ruleService.findByName(ab.getRuleName());

            if(group == null) {
                model.addAttribute("error", ab.getGroupName() + " 分组不存在");
                return "msg/error";
            }

            if(rule == null) {
                model.addAttribute("error", ab.getRuleName() + " 规则不存在");
                return "msg/error";
            }

            apService.save(ap, group, rule);
        }
        else {
            model.addAttribute("error", "未指定分组");
            return "msg/error";
        }

        return "msg/success";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(APBean ab) {
        APInfo ap = null;

        if(ab.getId() > 0) {
            ap = apService.findOne(ab.getId());

            DTOUtil.mapTo(ab, ap);
            apService.save(ap, ruleService.findByName(ab.getRuleName()));

        }

        return "msg/success";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(APBean ab) {

        if(ab.getId() > 0) {
            apService.delete(ab.getId());
        }

        return "msg/success";
    }


    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "groupName", required = false) String groupName,
                       @RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {

        Page<APInfo> aps = null;

        if(groupName != null && groupName.length() != 0) {
            Group group = groupService.findByName(groupName);
            if(group != null) {
                aps = apService.findAllByGroup(pb, group);

                ring(aps);
                model.addAttribute("total", aps.getTotalElements());
                model.addAttribute("rows", aps.getContent());
                return "jsonTemplate";
            }
            else {
                model.addAttribute("error", groupName + " 不存在");
                return "msg/error";
            }
        }
        else if(search != null && search.length() != 0) {
            aps = apService.findAllByMac(pb, search);
        }
        else {
            aps = apService.findAll(pb);
        }

        ring(aps);
        model.addAttribute("total", aps.getTotalElements());
        model.addAttribute("rows", aps.getContent());

        return "jsonTemplate";
    }


    public void ring(Page<APInfo> aps) {
        for(APInfo ap : aps.getContent()) {
            if(ap.getRule() != null) {
                ap.setRuleName(ap.getRule().getName());
            }

            if(ap.getGroup() != null) {
                ap.setGroupName(ap.getGroup().getName());
            }
        }
    }



}
