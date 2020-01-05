/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.app;

import java.util.List;

/**
 *
 * @author Erica Moisei
 * @date 16/11/2019
 * @project Java Moody Chat Bot
 */
public class MoodValue {

    private String mood;
    private double value;

    /**
     * Calculating the over all value of the message Setting the value and mood
     * parameters
     *
     * @param sentiments
     */
    public MoodValue(List<String> sentiments) {
        this.value = getOverallValue(sentiments);
        this.mood = getMoodState();
    }

//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getMood() {
        return mood;
    }

    public double getValue() {
        return value;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Identifying the overall mood of the message
     *
     * @return
     */
    private String getMoodState() {
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
     * Calculating the overall value of the message
     *
     * @param sentiments
     * @return overall value
     */
    private double getOverallValue(List<String> sentiments) {
        double value = 0;

        //adding value for statistics per sentence
        for (String s : sentiments) {
            if (s.equalsIgnoreCase("Very Positive")) {
                value += 25;
            } else if (s.equalsIgnoreCase("Positive")) {
                value += 10;
            } else if (s.equalsIgnoreCase("Negative")) {
                value += -10;
            } else if (s.equalsIgnoreCase("Very Negative")) {
                value += -25;
            }
        }
        return value / sentiments.size();
    }
//</editor-fold>
}
