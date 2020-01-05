/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.commands;

import bot.app.MoodyBotApp;
import bot.logger.DatabaseLogger;
import bot.models.HellModeState;
import bot.models.MoodyBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class HellMode extends ListenerAdapter {

    private MoodyBot bot;
    private final String ADMIN_ID = "345256663214981120"; //admin id

    /**
     * constructor with the bot
     *
     * @param bot
     */
    public HellMode(MoodyBot bot) {
        this.bot = bot;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        DatabaseLogger db = new DatabaseLogger();
        //checking the command

        //shows the current Hell Mode state
        if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "HellMode?")) {
            HellModeState hm = db.getLastHellMode();

            //build and send the message 
            EmbedBuilder success = new EmbedBuilder();
            success.setDescription("Currently Hell Mode is " + hm.getState().toUpperCase());
            success.setColor(hm.getState().equalsIgnoreCase("on") ? 0xdb2500 : 0x0087db); //Red color when Hell Mode is on
            event.getChannel().sendMessage(success.build()).queue();
            success.clear();
        } else if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "HellMode") && args.length > 1) {
            //Admin only command to change hell mode state on/off
            //checking the user if admin
            if (event.getAuthor().getId().equalsIgnoreCase(ADMIN_ID)) {
                //get current hellmode
                HellModeState hm = new HellModeState();

                if (args[1].equalsIgnoreCase("on")) {
                    //turn on hell mode
                    hm.setState("on");
                    //2 settings must be on for the hell mode
                    bot.setTrackSettings(true);
                    bot.setAnnonimusLog(false);
                    //save new config to db
                    bot.saveNewConfiguration();
                } else if (args[1].equalsIgnoreCase("off")) {
                    //turn of hell mode
                    hm.setState("off");
                }

                //setting message and title to off as a default for bot response 
                String msg = "Quiet times fell upon the chat.";
                String title = "Hell Mode is turned off.";

                //saving hell mode to db
                if (db.newHellMode(hm)) {
                    //change message and title if the state is on
                    if (hm.getState().equalsIgnoreCase("on")) {
                        msg = "User roles would be assignrd by personal statistics.\n"
                                + "If you have a Devil role, the restictions would be applied:\n"
                                + "\tno emojis, no reactions, no sending files, no voice, no live.";
                        title = "WARNING!\nHell Mode is ON.";
                    }
                }
                //build and send the response
                EmbedBuilder success = new EmbedBuilder();
                success.setTitle(title);
                success.setDescription(msg);
                success.setColor(hm.getState().equalsIgnoreCase("on") ? 0xdb2500 : 0x0087db); //Red color when Hell Mode is on
                event.getChannel().sendMessage(success.build()).queue();
                success.clear();
            }

        }
    }
}
