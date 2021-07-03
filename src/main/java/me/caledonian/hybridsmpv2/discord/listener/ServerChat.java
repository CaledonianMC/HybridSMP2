package me.caledonian.hybridsmpv2.discord.listener;

import me.caledonian.hybridsmpv2.listener.ChatFormatting;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerChat extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if(!e.getAuthor().isBot()){
            if(e.getChannel().getId().equals(Files.discord.getString("channels.server-chat"))){
                // Global Chat
                Bukkit.broadcastMessage(Utils.chat(Files.config.getString("chat-formatting.discord").replace("%user%", e.getAuthor().getAsTag()).replace("%message%", e.getMessage().getContentRaw())));
            }else if(e.getChannel().getId().equals(Files.discord.getString("channels.log-staff"))){
                // Staff Chat
                String format = Utils.chat(Files.msgs.getString("staffchat.format").replace("%player%", e.getAuthor().getAsTag()).replace("%server%", "Discord")) + e.getMessage().getContentRaw();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if(player.hasPermission(Files.perms.getString("admin.staffchat"))){
                        player.sendMessage(format);
                    }
                }
            }
        }
    }
}
