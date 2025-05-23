package com.mediquity.auto_restart.controller;

import com.mediquity.auto_restart.model.RemoteMonitorProperties;
import com.mediquity.auto_restart.service.RemoteProcessMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RemoteMonitorController {

    @Autowired
    private RemoteMonitorProperties properties;

    @Autowired
    private RemoteProcessMonitorService monitorService;

    @GetMapping("/select-ip")
    public String selectIpForm(Model model) {
        model.addAttribute("hosts", properties.getHosts());
        return "select-ip";
    }

    @GetMapping("/remote-monitor")
    public String showPage(@RequestParam String host, Model model) {
        model.addAttribute("processes", monitorService.getRemoteJavaProcesses( host));
        model.addAttribute("host", host);
        return "monitor";
    }

    @GetMapping("/remote-monitor/data")
    public String fetchData(@RequestParam String host,Model model) {
        model.addAttribute("processes", monitorService.getRemoteJavaProcesses(host));
        return "fragments/table :: table";
    }
    @PostMapping("/restart-pid")
    public String restartPid(@RequestParam String pid, @RequestParam String cmd,@RequestParam String host, RedirectAttributes redirectAttributes) {
        boolean success = monitorService.restartProcess(pid, cmd,host);
        redirectAttributes.addFlashAttribute("msg", success ? "Restarted successfully." : "Restart failed.");
        return "redirect:/select-ip";
    }

}
