/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.commands;

import NLP.SentimentHandler;
import bot.models.AnalyzerLog;
import bot.models.MoodyBot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import bot.app.MoodValue;
import bot.app.MoodyBotApp;
import bot.app.ResponseMessages;
import bot.logger.DatabaseLogger;
import bot.models.HellModeState;
import java.util.HashMap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class Mood extends ListenerAdapter {

    //public static NlpBot nlp;
    private static SentimentHandler handler;
    private ResponseMessages responses;
    private AnalyzerLog log;
    private MoodyBot bot;
    private final String ANGEL_ROLE = "651846421711945738";
    private final String FOLKS_ROLE = "651846477705904152";
    private final String DEVIL_ROLE = "651846520903041032";

    //contructor with responses and bot
    public Mood(ResponseMessages responses, MoodyBot mb) {
        this.responses = responses;
        this.bot = mb;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        //checking the commad
        if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "mood")) {
            if (args.length < 2) {
                EmbedBuilder usage = new EmbedBuilder();
                usage.setTitle("Specify a message.");
                usage.setDescription("Usage: `" + MoodyBotApp.prefix + "mood [your message]`");
                usage.setColor(0xfc2403);
                event.getChannel().sendMessage(usage.build()).queue();
                usage.clear();
            } else {
                try {
                    log = new AnalyzerLog();

                    // getting full message and adding it to the model + user id
                    log.setMessage(getMessageSting(args));
                    log.setUserId(event.getAuthor().getId());

                    //instantiating the handler and analizing the message
                    handler = new SentimentHandler();
                    List<String> sentimentList = handler.sentimentAnalyze(log.getMessage()); //mood results

                    //Identifying the over all value and mood of the message, writng to the log
                    MoodValue mv = new MoodValue(sentimentList);
                    log.setMood(mv.getMood());
                    log.setValue(mv.getValue());

                    //rigth to db if tracking statistics is on
                    if (this.bot.isTrackSettings()) {
                        log.saveLog(this.bot.isAnnonimusLog());

                        DatabaseLogger db = new DatabaseLogger();
                        //check if the user has more than 5 messages loged to db
                        if (db.userHasStatiscs(log.getUserId())) {

                            //get current hellmode
                            HellModeState hm = db.getLastHellMode();
                            //if hell mode is on check the role asigning
                            if (hm.getState().equalsIgnoreCase("on")) {
                                //Get user statistics
                                User user = event.getAuthor();
                                HashMap<String, Double> moodValues = db.getUserMood(user.getId());

                                if (moodValues.size() > 0) {
                                    Object firstKey = moodValues.keySet().toArray()[0];
                                    double value = moodValues.getOrDefault(firstKey, Double.MAX_VALUE);

                                    //find member, role and assign the role accordingly
                                    Member member = event.getGuild().getMemberById(log.getUserId());
                                    Role role = null;
                                    if (value < -10) {
                                        role = event.getGuild().getRoleById(DEVIL_ROLE); //Devil role
                                    } else if (value < 10) {
                                        event.getGuild().getRoleById(FOLKS_ROLE); //Folks role
                                    } else {
                                        role = event.getGuild().getRoleById(ANGEL_ROLE); //Angel role
                                    }
                                    //check if the user alreasy in that role, and add if not
                                    if (!member.getRoles().contains(role)) {
                                        event.getGuild().addRoleToMember(member, role).complete();
                                    }
                                }
                            }

                        }
                    }

                    //getting a random  bot response message by identified mood
                    String moodResponse = responses.moodRndResponse(log.getValue());

                    //build a resomse message
                    EmbedBuilder success = new EmbedBuilder();
                    success.setTitle("Mood identified: " + log.getMood());
                    success.setDescription(moodResponse);
                    success.setColor(log.getValue() < -10 ? 0xdb2500 : 0x25a816); //red color for bad mood
                    event.getChannel().sendMessage(success.build()).queue();
                    success.clear();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    /**
     * Building a full message without the prefix
     *
     * @param args arguments
     * @return message string
     */
    private String getMessageSting(String[] args) {
        List<String> message = new ArrayList<>(Arrays.asList(args));
        message.remove(0); // remouving the command
        //creating a full message
        StringBuilder sb = new StringBuilder();
        for (String s : message) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }

}
