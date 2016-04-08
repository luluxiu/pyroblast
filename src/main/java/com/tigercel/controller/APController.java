package com.tigercel.controller;

import com.tigercel.Bean.APBean;
import com.tigercel.Bean.PageBean;
import com.tigercel.Utils.DTOUtil;
import com.tigercel.model.APInfo;
import com.tigercel.service.APService;
import com.tigercel.service.GroupService;
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
@RequestMapping("ap")
public class APController {

    @Autowired
    private APService apService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        return "ap/index";
    }

    @RequestMapping(value = "new", method = POST)
    public String add(APBean ab) {

        APInfo ap = apService.findByName(ab.getName());

        if(ap != null) {
            return "msg/error";
        }
        ap = DTOUtil.map(ab, APInfo.class);

        if(ab.getGroupId() > 0) {
            apService.save(ap, groupService.findOne(ab.getGroupId()));
        }
        else {
            apService.save(ap);
        }


        return "msg/success";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(APBean ab) {

        APInfo ap = apService.findOne(ab.getId());

        DTOUtil.mapTo(ab, ap);
        if(ab.getGroupId() > 0) {
            apService.save(ap, groupService.findOne(ab.getGroupId()));
        }
        else {
            apService.save(ap);
        }

        return "msg/success";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(APBean ab) {

        apService.delete(ab.getId());

        return "msg/success";
    }


    @RequestMapping(value = "show", method = GET)
    public String show(PageBean pb, Model model) {

        Page<APInfo> aps = apService.findAll(pb);

        model.addAttribute("total", aps.getTotalElements());
        model.addAttribute("rows", aps.getContent());

        return "jsonTemplate";
    }
}
