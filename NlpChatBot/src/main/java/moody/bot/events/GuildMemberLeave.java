/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.events;

import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class GuildMemberLeave extends ListenerAdapter {

    String[] messages = {"[member] left, the party's over.",
        "[member] just left the serve.",
        "[member] is not our friend anymore...",
        "[member] left the chat. He is so wired...",
        "[member] quit. 👋🏻"};

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        //get a randome goodbye message
        Random rand = new Random();
        int number = rand.nextInt(messages.length);
        
        //styling the response
        EmbedBuilder join = new EmbedBuilder();
        join.setColor(0xc2c1c0);
        join.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));

        //sending message to the chat
        event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();

    }
}
