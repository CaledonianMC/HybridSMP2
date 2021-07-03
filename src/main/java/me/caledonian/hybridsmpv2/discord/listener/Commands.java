package me.caledonian.hybridsmpv2.discord.listener;

import me.caledonian.hybridsmpv2.discord.DiscordMessenger;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if (!e.getAuthor().isBot()) {

            if(args[0].equalsIgnoreCase("-online")){
                DiscordMessenger.onlineCmd(e.getJDA(), e.getChannel());
            }
        }
    }
}
