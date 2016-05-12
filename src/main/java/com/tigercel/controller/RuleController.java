package com.tigercel.controller;

import com.tigercel.Bean.PageBean;
import com.tigercel.Bean.RuleBean;
import com.tigercel.Utils.DTOUtil;
import com.tigercel.model.Rule;
import com.tigercel.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/3/30.
 */
@Controller
@RequestMapping("rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    private static Logger logger = LoggerFactory.getLogger(RuleController.class);

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        model.addAttribute("menu", "rules");
        model.addAttribute("title", "规则列表");

        return "rule/index";
    }

    @RequestMapping(value = "new", method = POST)
    public String add(RuleBean rb, Model model) {

        Rule rule = ruleService.findByName(rb.getName());

        if(rule != null) {
            model.addAttribute("error", "规则名已存在");
            return "msg/error";
        }
        rule = DTOUtil.map(rb, Rule.class);
        ruleService.save(rule);

        return "msg/success";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(RuleBean rb) {
        Rule rule = null;

        if(rb.getId() > 0) {
            rule = ruleService.findOne(rb.getId());
            DTOUtil.mapTo(rb, rule);
            ruleService.save(rule);
        }

        return "msg/success";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(RuleBean rb) {

        if(rb.getId() > 0) {
            ruleService.delete(rb.getId());
        }

        return "msg/success";
    }


    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {
        Page<Rule> rules = null;

        if(search != null && search.length() != 0) {
            rules = ruleService.findAllByName(pb, search);
        }
        else {
            rules = ruleService.findAll(pb);
        }

        model.addAttribute("total", rules.getTotalElements());
        model.addAttribute("rows", rules.getContent());

        return "jsonTemplate";
    }

}
