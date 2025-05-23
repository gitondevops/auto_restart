package com.mediquity.auto_restart.model;

public class PortInfo {
    private int port;
    private String state;
    private String warFile;
    private boolean restartable;

    // Constructor
    public PortInfo(int port, String state, String warFile, boolean restartable) {
        this.port = port;
        this.state = state;
        this.warFile = warFile;
        this.restartable = restartable;
    }

    // Getters & setters
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getWarFile() { return warFile; }
    public void setWarFile(String warFile) { this.warFile = warFile; }
    public boolean isRestartable() { return restartable; }
    public void setRestartable(boolean restartable) { this.restartable = restartable; }
}
