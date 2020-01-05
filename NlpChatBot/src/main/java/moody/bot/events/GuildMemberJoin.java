/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moody.bot.events;

import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class GuildMemberJoin extends ListenerAdapter {

    String[] messages = {"Never gonna give [member] up. Never let [member] down!",
        "Hey! Listen! [member] has joined!",
        "We've been expecting you, [member].",
        "Swoooosh. [member] just landed.",
        "Brace yourselves. [member] just joined the server.",
        "A wild [member] appeared.",
        "New member is here! Hello [member]!"};

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        //get a randome welcome message
        Random rand = new Random();
        int number = rand.nextInt(messages.length);
        
        //styling the response
        EmbedBuilder join = new EmbedBuilder();
        join.setColor(0x66d8ff);
        join.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
        
        //sending message to the chat
        event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();

    }
}
