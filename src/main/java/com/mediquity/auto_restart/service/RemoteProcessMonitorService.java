package com.mediquity.auto_restart.service;

import com.jcraft.jsch.*;
import com.mediquity.auto_restart.model.RemoteProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RemoteProcessMonitorService {

//    private final String host = "152.67.4.72";
//    private final String user = "ubuntu";
//    private final String keyPath = "/home/prashant/.ssh/id_rsa"; // or use password if needed
    @Value("${monitor.remote.user}")
    private String user;
    @Value("${monitor.remote.key-path}")
    private String keyPath;



    public List<Map<String, String>> getRemoteJavaProcesses(String host) {
        List<Map<String, String>> processes = new ArrayList<>();
        String command = "ps -eo pid,pcpu,cmd | grep java | grep -v grep";

        try {
            JSch jsch = new JSch();
            jsch.addIdentity(keyPath);

            Session session = jsch.getSession(user, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);
            BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            channel.connect();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+", 3);
                if (parts.length >= 3) {
                    Map<String, String> info = new HashMap<>();
                    info.put("pid", parts[0]);
                    info.put("cpu", parts[1] + " %");
                    info.put("cmd", parts[2]);
                    processes.add(info);
                }
            }

            reader.close();
            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return processes;
    }

    public boolean restartProcess(String pid, String cmd,String host) {
        String killCmd = "kill -9 " + pid;
        String warFile = extractWarFile(cmd);
        if (warFile == null) return false;
        String restartCmd = "nohup java -jar " + warFile + " > logs/dev/null 2>&1 &";

        try {
            JSch jsch = new JSch();
            jsch.addIdentity(keyPath);

            Session session = jsch.getSession(user, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(killCmd + " && sleep 2 && " + restartCmd);
            channel.connect();

            channel.disconnect();
            session.disconnect();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String extractWarFile(String cmd) {
        Pattern pattern = Pattern.compile("java\\s+-jar\\s+([^\\s]+\\.war)");
        Matcher matcher = pattern.matcher(cmd);
        return matcher.find() ? matcher.group(1) : null;
    }


}
