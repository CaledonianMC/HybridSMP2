package me.caledonian.hybridsmpv2.discord;

import me.caledonian.hybridsmpv2.HybridSMPv2;
import me.caledonian.hybridsmpv2.discord.listener.Commands;
import me.caledonian.hybridsmpv2.discord.listener.ServerChat;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Discord {

    public static void setup(HybridSMPv2 m, JDA bot){
        bot.getPresence().setActivity(Activity.watching(Files.discord.getString("setup.activity").replace("%num%", "0")));
        try{
            bot.getPresence().setStatus(OnlineStatus.valueOf(Files.discord.getString("setup.status")));}catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "An error occurd whilst setting the bot status. Status must be: ONLINE, IDLE, DO_NOT_DISTURB, OFFLINE");}

        // Events
        bot.addEventListener(new ServerChat());
        bot.addEventListener(new Commands());
        // Finally
        Logger.log(Logger.LogLevel.SUCCESS, "All discord events and builders are registered. The bot is now operational.");
    }

    public static void shutdown(JDA bot){
        if(bot != null){
            Logger.log(Logger.LogLevel.INFO, "Attempting to shutdown the bot");
            bot.shutdown();
        }else{Logger.log(Logger.LogLevel.SUCCESS, "All discord events and builders are registered. The bot is now operational.");}
    }
}
