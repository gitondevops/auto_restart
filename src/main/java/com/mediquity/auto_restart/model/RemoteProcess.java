package com.mediquity.auto_restart.model;


public class RemoteProcess {
    private String pid;
    private String cmd;

    public RemoteProcess(String pid, String cmd) {
        this.pid = pid;
        this.cmd = cmd;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
