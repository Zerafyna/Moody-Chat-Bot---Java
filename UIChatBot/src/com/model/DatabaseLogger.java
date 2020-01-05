/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class DatabaseLogger {

    private String url = "";
    private String user = "";
    private String password = "";

    public DatabaseLogger() {
        try {
            Properties props = DALHelper.getProperties();
            this.url = props.getProperty("mysql.url");
            this.user = props.getProperty("mysql.username");
            this.password = props.getProperty("mysql.password");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getting overall chat mood as a string representation
     * @return mood string
     */
    public String getOverallMood() {
        String sql = "SELECT Sum(Value) / (SELECT Count(Value) FROM analyzerlog) FROM analyzerlog;";
        String mood = null;
        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (Statement st = conn.createStatement()) {
                    try (ResultSet rs = st.executeQuery(sql)) {
                        while (rs.next()) {
                            //find string representation
                            String sum = rs.getString(1);
                            System.out.println(sum);
                            mood = getMoodState(Double.parseDouble(sum));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mood;
    }

    /**
     * calculating the mood string representation
     * @param value
     * @return 
     */
    private String getMoodState(double value) {
        String moodState = null;

        if (value < -15) {
            moodState = "Verry Negative";
        } else if (value < -5) {
            moodState = "Negative";
        } else if (value < 5) {
            moodState = "Neutral";
        } else if (value < 15) {
            moodState = "Positive";
        } else {
            moodState = "Verry Positive";
        }
        return moodState;
    }

    /**
     * getting AnalyzerLog Messages limiting by number
     *
     * @return list of AnalyzerLog
     */
    public List<AnalyzerLog> getAnalyzerLogs(int num) {
        List<AnalyzerLog> logs = new ArrayList();
        String sql = "SELECT * FROM analyzerlog ORDER BY Date DESC LIMIT ?;";

        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, num);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            logs.add(populateAnalyzerLog(rs));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return logs;
    }

    /**
     * Populating properties of the Analyzer log class
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private AnalyzerLog populateAnalyzerLog(ResultSet rs) throws SQLException {
        AnalyzerLog log = new AnalyzerLog();
        log.setId(rs.getInt("Id"));
        log.setDateTime(new java.util.Date(rs.getTimestamp("Date").getTime()));
        log.setUserId(rs.getString("UserId"));
        log.setMessage(rs.getString("Message"));
        log.setMood(rs.getString("Mood"));
        log.setValue(rs.getDouble("Value"));

        return log;
    }

    /**
     * Getting the latest settings of the bot
     *
     * @return
     */
    public MoodyBot getLastConfiguration() {

        String sql = "SELECT * FROM moodybot.botsettingslog ORDER BY Date DESC LIMIT 1;";
        MoodyBot mb = new MoodyBot();

        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (Statement st = conn.createStatement()) {
                    try (ResultSet rs = st.executeQuery(sql)) {
                        while (rs.next()) {
                            mb = populateMoodyBot(rs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return mb;
    }

    /**
     * Getting a list of bots settings with number of rows
     *
     * @return
     */
    public List<MoodyBot> getConfigurations(int num) {
        List<MoodyBot> mbList = new ArrayList();
        String sql = "SELECT * FROM moodybot.botsettingslog ORDER BY Date DESC LIMIT ?;";
        MoodyBot mb = new MoodyBot();

        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, num);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            mb = populateMoodyBot(rs);
                            mbList.add(mb);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return mbList;
    }

    /**
     * recording new bot configuration to db
     *
     * @param mb
     * @return
     */
    public boolean setNewBotConfig(MoodyBot mb) {
        boolean result = false;
        try {
            String sql = "INSERT INTO botsettingslog (Date, TrackStatistics, WatchingActivity, AnnonimusLog)  VALUES (NOW(), ?, ?, ?);";
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setBoolean(1, mb.isTrackSettings());
                    ps.setString(2, mb.getActivity());
                    ps.setBoolean(3, mb.isAnnonimusLog());
                    int recordsAffected = ps.executeUpdate();
                    result = recordsAffected == 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Populating properties of the Moody Bot class
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private MoodyBot populateMoodyBot(ResultSet rs) throws SQLException {
        MoodyBot mb = new MoodyBot();
        mb.setId(rs.getInt("ID"));
        mb.setDate(new java.util.Date(rs.getTimestamp("Date").getTime()));
        mb.setActivity(rs.getString("WatchingActivity"));
        mb.setAnnonimusLog(rs.getBoolean("AnnonimusLog"));
        mb.setTrackSettings(rs.getBoolean("TrackStatistics"));
        return mb;
    }
}
