/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.commands;

import bot.app.MoodyBotApp;
import bot.logger.DatabaseLogger;
import java.text.DecimalFormat;
import java.util.HashMap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class UserStatictics extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        //checking the first and second argument for the command
        if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "my") && args[1].equalsIgnoreCase("statistics")) {
            DecimalFormat df2 = new DecimalFormat("#.##"); //2 decimal places formatter

            EmbedBuilder response = new EmbedBuilder();
            //default message if the user doesn't have statistic on (more than 5 messager recorded)
            String infoMsg = "You don't have a statistics yet.";

            //getting the author of the message sent
            User user = event.getAuthor();
            DatabaseLogger db = new DatabaseLogger();

            //checking if user has statistics 
            if (db.userHasStatiscs(user.getId())) {
                //getting user statistics
                HashMap<String, Double> moodValues = db.getUserMood(user.getId());
                if (moodValues.size() > 0) {
                    Object firstKey = moodValues.keySet().toArray()[0];

                    infoMsg = "Your overall mood is:  " + firstKey.toString() + "\n"
                            + "The score is: " + df2.format(moodValues.getOrDefault(firstKey, Double.MAX_VALUE));
                }
            }
            //styling response message
            response.setTitle("Peronal statistics report");
            response.setDescription(infoMsg);
            response.setColor(0xfc6203);

            //sending to the user a private message with the response
            user.openPrivateChannel().queue((channel) -> {
                channel.sendMessage(response.build()).queue();
            });

            response.clear();
        }

    }
}
