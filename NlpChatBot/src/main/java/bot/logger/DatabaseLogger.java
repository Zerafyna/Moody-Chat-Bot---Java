/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logger;

import bot.models.AnalyzerLog;
import bot.models.HellModeState;
import bot.models.MoodyBot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
     * Writing the analyzer log into the db
     *
     * @param log
     * @param annonimus
     */
    public boolean newAnalizerLog(AnalyzerLog log, boolean annonimousLog) {
        boolean result = false;
        try {
            String userId = annonimousLog ? null : log.getUserId(); // if the annonymus if on, the id will be null

            String sql = "INSERT INTO analyzerlog (Date, UserId, Message, Mood, Value) VALUES (NOW(), ?, ?, ?, ?);";
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, userId);
                    ps.setString(2, log.getMessage());
                    ps.setString(3, log.getMood());
                    ps.setDouble(4, log.getValue());

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
     * Getting the latest settings of the bot
     *
     * @return
     */
    public MoodyBot getConfigurations() {

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
     * Populating properties of the Moody Bot class
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private MoodyBot populateMoodyBot(ResultSet rs) throws SQLException {
        MoodyBot mb = new MoodyBot();
        mb.setId(rs.getInt("ID"));
        mb.setDate(rs.getDate("Date"));
        mb.setActivity(rs.getString("WatchingActivity"));
        mb.setAnnonimusLog(rs.getBoolean("AnnonimusLog"));
        mb.setTrackSettings(rs.getBoolean("TrackStatistics"));
        return mb;
    }

    /**
     * Getting chats overall mood string and value
     *
     * @return
     */
    public HashMap<String, Double> getOverallMood() {
        String sql = "SELECT Sum(Value) / (SELECT Count(Value) FROM analyzerlog) FROM analyzerlog;";
        HashMap<String, Double> moodValues = new HashMap<String, Double>();
        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (Statement st = conn.createStatement()) {
                    try (ResultSet rs = st.executeQuery(sql)) {
                        while (rs.next()) {
                            //find string representation
                            String sum = rs.getString(1);
                            System.out.println(sum);
                            setMoodState(Double.parseDouble(sum), moodValues);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return moodValues;
    }

    /**
     * Geting personal overall mood string and value by User Id
     *
     * @param id
     * @return
     */
    public HashMap<String, Double> getUserMood(String id) {
        HashMap<String, Double> moodValues = new HashMap<String, Double>();

        String sql = "SELECT Sum(Value) / (SELECT Count(Value) FROM analyzerlog WHERE UserId = ?) FROM analyzerlog WHERE UserId = ?;";
        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, id);
                    ps.setString(2, id);
                    try (ResultSet rs = (ResultSet) ps.executeQuery()) {
                        while (rs.next()) {
                            double value = rs.getDouble(1);
                            setMoodState(value, moodValues);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return moodValues;
    }

    /**
     * defining mood string representation for the value
     *
     * @param value
     * @param moodValues
     * @return
     */
    private void setMoodState(double value, HashMap<String, Double> moodValues) {
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
        moodValues.put(moodState, value);
    }

    /**
     * Checks if user has personal statistic (more than 2 messages saved in db)
     *
     * @param id
     * @return
     */
    public boolean userHasStatiscs(String id) {
        boolean result = false;
        String sql = "SELECT COUNT(Id) FROM analyzerlog WHERE UserId = ?;";

        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, id);
                    try (ResultSet rs = (ResultSet) ps.executeQuery()) {
                        while (rs.next()) {
                            result = rs.getDouble(1) > 5;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
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
     * Getting the last hell mode config recoded in db
     *
     * @return
     */
    public HellModeState getLastHellMode() {
        HellModeState state = new HellModeState();
        String sql = "SELECT * FROM moodybot.hellmode order by Date DESC LIMIT 1;";

        try {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (Statement st = conn.createStatement()) {
                    try (ResultSet rs = st.executeQuery(sql)) {
                        while (rs.next()) {
                            state.setDateTime(new java.util.Date(rs.getTimestamp("Date").getTime()));
                            state.setState(rs.getString("State"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return state;
    }

    /**
     * recording a new config for hell mode
     *
     * @param state
     * @return
     */
    public boolean newHellMode(HellModeState state) {
        boolean result = false;
        try {
            String sql = "INSERT INTO hellmode (Date, State) VALUES(now(), ?); ";
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, state.getState());
                    int recordsAffected = ps.executeUpdate();
                    result = recordsAffected == 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return result;

    }
}
