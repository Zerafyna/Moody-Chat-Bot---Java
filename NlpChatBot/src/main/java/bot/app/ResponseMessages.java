/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.app;

import java.util.Arrays;
import java.util.List;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class ResponseMessages {

    private final List<String> VeryNagetive = Arrays.asList("You are very negative.", "I didn't know before that people can be so pessimistic.",
            "You have a bad influece on me. 😒", "Stop whining and being aggressive!", "Our conversation is useless, cheer up!",
            "You are negative, but I will try to support you. Here is a joke:\nNeed cheering up? Start a fight with somebody when they have the hiccoughs! ☺️");

    private final List<String> Nagetive = Arrays.asList("You are kinda negative today.", "Don't be pessimistic. Smile!", "Don't be sad, cheer up!",
            "I feel like you are upset. Here is a joke: \n - Why do we tell actors to “break a leg?”\n- Because every play has a cast 😄",
            "It is not the right time to be sad.");

    private final List<String> Neutral = Arrays.asList("You are too neutral, go unwind.", "Hmm... Your message is neutral. Are you hiding something from me?",
            "I need to make your day even better! Here is a joke:\nDentist: “This will hurt a little.”\nPatient: “OK.”\nDentist: "
            + "“I’ve been having an affair with your wife for a while now.” 😅", "You are boring, my friend.", "You make me wanna fall asleep... 😴");

    private final List<String> Positive = Arrays.asList("You are in a good mood today!", "You are positive. Good job!", "Our conversations are interesting!",
            "I see you are in a good mood today. I hope you don't mind to hear a joke:\nA wife is like a hand grenade. Take off the ring and say good bye to your house. 😁",
            "You are a positive person. I like that!");

    private final List<String> VaryPositive = Arrays.asList("Your message made me smile!", "You are very positive today!", "Haha! I want you to be my best friend!",
            "Super positive person is here! I am glad that you are here today!", "I have a joke for a great person like you:\n- Waiter, there’s a fly twitching in my soup!”\n"
            + "- And what do you expect for the price? A ballet?! 💃🏻");

    //<editor-fold defaultstate="collapsed" desc="Getters and Constructors">
    public List<String> getVeryNagetive() {
        return VeryNagetive;
    }

    public List<String> getNagetive() {
        return Nagetive;
    }

    public List<String> getNeutral() {
        return Neutral;
    }

    public List<String> getPositive() {
        return Positive;
    }

    public List<String> getVeryPositive() {
        return VaryPositive;
    }

    public ResponseMessages() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Gets a random response from a list using random number generator within
     * the range of the List size
     *
     * @return
     */
    public String getRndVNegative() {
        int range = this.getVeryNagetive().size();
        return this.getVeryNagetive().get(this.getRandomNumber(range));
    }

    /**
     * Gets a random response from a list using random number generator within
     * the range of the List size
     *
     * @return
     */
    public String getRndNegative() {

        int range = this.getNagetive().size();
        return this.getNagetive().get(this.getRandomNumber(range));
    }

    /**
     * Gets a random response from a list using random number generator within
     * the range of the List size
     *
     * @return
     */
    public String getRndNeutral() {
        int range = this.getNeutral().size();
        return this.getNeutral().get(this.getRandomNumber(range));
    }

    /**
     * Gets a random response from a list using random number generator within
     * the range of the List size
     *
     * @return
     */
    public String getRndPositive() {
        int range = this.getPositive().size();
        return this.getPositive().get(this.getRandomNumber(range));
    }

    /**
     * Gets a random response from a list using random number generator within
     * the range of the List size
     *
     * @return
     */
    public String getRndVPositive() {
        int range = this.getVeryPositive().size();
        return this.getVeryPositive().get(ResponseMessages.getRandomNumber(range));
    }

    /**
     * finding the random number within the range of 0 and passed number
     *
     * @param maxValue
     * @return
     */
    public static int getRandomNumber(double maxValue) {
        return (int) (maxValue * Math.random());
    }

    /**
     * returns a random answer string by the overall message value
     *
     * @param value
     * @return Random message from the List
     */
    public String moodRndResponse(double value) {
        String moodString = null;
        if (value < -15) {
            moodString = this.getRndVNegative();
        } else if (value < -5) {
            moodString = this.getRndNegative();
        } else if (value < 5) {
            moodString = this.getRndNeutral();
        } else if (value < 15) {
            moodString = this.getRndPositive();
        } else {
            moodString = this.getRndVPositive();
        }
        return moodString;
    }
//</editor-fold>

}
