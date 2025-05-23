package com.mediquity.auto_restart.model;

public class PortInfo {
    private int port;
    private String warFile;
    private int pid;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWarFile() {
        return warFile;
    }

    public void setWarFile(String warFile) {
        this.warFile = warFile;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public boolean isRestartable() {
        return restartable;
    }

    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    private boolean restartable;

    public PortInfo(String state, int port, String warFile, int pid, boolean restartable) {
        this.port = port;
        // LISTEN, ESTABLISHED
        this.warFile = warFile;
        this.pid = pid;
        this.restartable = restartable;
    }

    // Getters and setters
}
