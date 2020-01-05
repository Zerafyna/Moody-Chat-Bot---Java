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
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class ChatStatistics extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "chat")) {
            //checks the command
            if (args[1].equalsIgnoreCase("statistics")) {
                DecimalFormat df2 = new DecimalFormat("#.##"); //format for 2 decimal places
                EmbedBuilder response = new EmbedBuilder();

                //get chat overall statstics
                DatabaseLogger db = new DatabaseLogger();
                HashMap<String, Double> moodValues = db.getOverallMood();
                Object firstKey = moodValues.keySet().toArray()[0];

                //setting the message
                String infoMsg = "The chat has overall mood of:  " + firstKey.toString() + "\n"
                        + "The score is: " + df2.format(moodValues.getOrDefault(firstKey, Double.MAX_VALUE));

                //styling the response
                response.setTitle("Statistics Report");
                response.setDescription(infoMsg);
                response.setColor(0xfc6203);

                //sending a response
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(response.build()).queue();

                response.clear();
            }

        }
    }
}
