package bot.app;

import bot.logger.DatabaseLogger;
import bot.models.MoodyBot;
import java.net.ServerSocket;
import javax.security.auth.login.LoginException;
import moody.bot.commands.*;
import moody.bot.events.GuildMemberJoin;
import moody.bot.events.GuildMemberLeave;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

/**
 * @author Erica Moisei
 * @date 21/10/2019
 * @project Java Moody Chat Bot
 */
public class MoodyBotApp {

    public static String prefix = "~"; //prefix for chat, easy to change here
    public static ResponseMessages responses = new ResponseMessages();
    public static DatabaseLogger db = new DatabaseLogger();
    public static MoodyBot mb = db.getConfigurations();
    private static JDABuilder builder;

    /**
     * Launching MoodyBot and opening TCP Server connection on 4444 port
     *
     * @param args the command line arguments
     * @throws javax.security.auth.login.LoginException
     */
    public static void main(String[] args) throws LoginException {
        // JDA builder with Token
        builder = new JDABuilder("NjM1OTI5MDU4OTk1MjA4MjUy.Xa5I-w.RVxNwm8KTA3mJGzFzyLIcVqYPzk");

        //using bot's token to setstatus and activity
        builder.setStatus(OnlineStatus.IDLE);

        //setting Activity from the model
        String activity = mb.getActivity() == null ? "boring comedy shows." : mb.getActivity();
        builder.setActivity(Activity.watching(activity));

        //Adding events listener
        builder.addEventListeners(new HellMode(mb), new UpdateWatching(mb), new Info(), new Mood(responses, mb),
                new UserStatictics(), new ChatStatistics(), new GuildMemberJoin(), new GuildMemberLeave());

        builder.build();

        //Lounching TCP server to listen the call from the Cliebnt (UI) to update bot from DB
        try {
            TCPServer.launchServer(new ServerSocket(4444));
        } catch (Exception e) {
            p(e.getMessage());
        }
    }

    static void p(String msg) {
        System.out.println(msg);
    }
}
