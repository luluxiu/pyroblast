package com.tigercel.controller;

import com.tigercel.Bean.PageBean;
import com.tigercel.model.Connection;
import com.tigercel.service.APService;
import com.tigercel.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by freedom on 2016/3/30.
 */
@Controller
@RequestMapping("conn")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private APService apService;

    @RequestMapping(value = "", method = GET)
    public String index() {

        return "conn/index";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam("apId") Long apId, PageBean pb, Model model) {

        Page<Connection> conns = connectionService.findAllByAP(apService.findOne(apId), pb);

        model.addAttribute("total", conns.getTotalElements());
        model.addAttribute("rows", conns.getContent());

        return "jsonTemplate";
    }


    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam("mac") String mac, PageBean pb, Model model) {

        Page<Connection> conns = connectionService.findAllByMac(mac, pb);

        model.addAttribute("total", conns.getTotalElements());
        model.addAttribute("rows", conns.getContent());

        return "jsonTemplate";
    }
}
