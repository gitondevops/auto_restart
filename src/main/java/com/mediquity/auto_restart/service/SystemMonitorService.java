package com.mediquity.auto_restart.service;

import com.mediquity.auto_restart.model.PortInfo;
import com.mediquity.auto_restart.model.SystemMetrics;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemMonitorService {

    public List<PortInfo> getPortInfoList() {
        List<PortInfo> ports = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec("sudo lsof -nP -iTCP -sTCP:LISTEN");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(".war")) {
                    // extract port, pid, state, warFile etc.
                    // Add logic here based on regex or split()
                }
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ports;
    }

    public SystemMetrics getSystemMetrics() {
        // Parse `top` or `free -h` output and return CPU and memory usage
        return new SystemMetrics("23%", "1.2 GB / 4 GB");
    }

    public boolean restartPort(int port) {
        try {
            ProcessBuilder pb = new ProcessBuilder("./restart-war-by-port.sh", String.valueOf(port));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
