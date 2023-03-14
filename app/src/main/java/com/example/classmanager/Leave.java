package com.example.classmanager;

public class Leave {
    private String name;
    private String date;
    private String reason;

    public Leave(String name, String date, String reason) {
        this.name = name;
        this.date = date;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
