/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.commands;

import bot.app.MoodyBotApp;
import bot.models.MoodyBot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class UpdateWatching extends ListenerAdapter {

    private MoodyBot bot;

    public UpdateWatching(MoodyBot mb) {
        this.bot = mb;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        //checking the command
        if (args[0].equalsIgnoreCase(MoodyBotApp.prefix + "UpdateActivity")) {
            //updates watching activity from bot's last update
            event.getJDA().getPresence().setActivity(Activity.watching(bot.getActivity()));

        }
    }
}
