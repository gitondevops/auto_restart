package com.mediquity.auto_restart.controller;

import com.mediquity.auto_restart.service.SystemMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MonitorController {

    @Autowired
    private SystemMonitorService monitorService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("ports", monitorService.getPortInfoList());
        model.addAttribute("metrics", monitorService.getSystemMetrics());
        return "dashboard";
    }

    @PostMapping("/restart/{port}")
    public String restart(@PathVariable int port) {
        monitorService.restartPort(port);
        return "redirect:/";
    }
}

