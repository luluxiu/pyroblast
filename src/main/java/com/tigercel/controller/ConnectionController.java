package com.tigercel.controller;

import com.tigercel.Bean.PageBean;
import com.tigercel.model.APInfo;
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
    public String index(@RequestParam(value = "apName", required = false) String apName,
                        Model model) {
        APInfo ap = null;

        model.addAttribute("menu", "conns");
        if(apName != null) {
            ap = apService.findByName(apName);
            if(ap != null) {
                model.addAttribute("title", apName + "  (" + ap.getMac() + ")");
                model.addAttribute("apname", apName);
            }
        }
        else {
            model.addAttribute("title", "连接列表");
        }

        return "conn/index";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "apName", required = false) String apName,
                       @RequestParam(value = "search" , required = false) String mac,
                       PageBean pb, Model model) {

        Page<Connection> conns = null;

        if(apName != null && apName.length() != 0) {
            APInfo ap = apService.findByName(apName);
            if(ap != null) {
                conns = connectionService.findAllByAP(ap, pb);

                model.addAttribute("total", conns.getTotalElements());
                model.addAttribute("rows", conns.getContent());
                return "jsonTemplate";
            }
            else {
                model.addAttribute("error", apName + " 不存在");
                return "msg/error";
            }
        }
        else if(mac != null && mac.length() != 0) {
            conns = connectionService.findAllByMac(mac, pb);
        }
        else {
            conns = connectionService.findAll(pb);

        }

        model.addAttribute("total", conns.getTotalElements());
        model.addAttribute("rows", conns.getContent());

        return "jsonTemplate";
    }

}
