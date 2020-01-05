/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.commands;

import bot.app.MoodyBotApp;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class Info extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String infoMsg = "Hello! I am the moodiest chat bot.\n"
                + "I can define your sentiments by monitoring your messages.\n\n"
                + "Here are my commands:\n"
                + "`~info`\n`~mood [message]` -  to analize your message\n"
                + "`~chat statistics` - shows overall chat mood\n"
                + "`~my statistics` - shows your personal mood statistics\n"
                + "`~UpdateActivity` - sets the lates watching activity configured\n"
                + "`~HellMode?` - shows current Hell Mode state\n\n"
                + "To defind human sentiments I use free and open source Stanford CoreNLP - Natural language software: "
                + "https://stanfordnlp.github.io/CoreNLP/";

        if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "info")) {
            //build info message
            EmbedBuilder info = new EmbedBuilder();

            info.setTitle("👻 Moody Bot"); //title
            info.setDescription(infoMsg); //message
            info.setColor(0x1649a8); //color
            info.setFooter("Created by Erica Moisei", event.getChannel().getManager().getGuild().getOwner().getUser().getAvatarUrl()); //footer

            event.getChannel().sendTyping().queue(); //typing animation
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }
}
