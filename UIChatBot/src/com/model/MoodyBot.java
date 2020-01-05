/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.Date;
import java.util.List;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class MoodyBot {

    //bot class
    private int id;
    private Date date;
    private boolean trackSettings;
    private boolean annonimusLog;
    private String activity;
    private DatabaseLogger db = new DatabaseLogger();

    public MoodyBot() {
    }

//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isTrackSettings() {
        return trackSettings;
    }

    public void setTrackSettings(boolean TrackSettings) {
        this.trackSettings = TrackSettings;
    }

    public boolean isAnnonimusLog() {
        return annonimusLog;
    }

    public void setAnnonimusLog(boolean AnnonimusLog) {
        this.annonimusLog = AnnonimusLog;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String Activity) {
        this.activity = Activity;
    }


//</editor-fold>

    public String getChatOverallMood() {
        return db.getOverallMood();
    }

    public MoodyBot getLastConfiguration() {
        return db.getLastConfiguration();
    }

    public List<MoodyBot> getBotConfigurations(int num) {
        return db.getConfigurations(num);
    }

    public boolean saveNewConfiguration(MoodyBot mb) {
        return db.setNewBotConfig(mb);
    }
}
