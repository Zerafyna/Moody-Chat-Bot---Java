/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.Date;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class AnalyzerLog {

    private int id;
    private Date dateTime;
    private String userId;
    private String message;
    private String mood;
    private double value;

    public AnalyzerLog() {
    }

//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date DateTime) {
        this.dateTime = DateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String UserId) {
        this.userId = UserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String Result) {
        this.mood = Result;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double Value) {
        this.value = Value;
    }

//</editor-fold>
}
