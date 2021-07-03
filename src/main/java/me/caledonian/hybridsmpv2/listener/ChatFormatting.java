package me.caledonian.hybridsmpv2.listener;

import me.caledonian.hybridsmpv2.commands.player.GlobalChat;
import me.caledonian.hybridsmpv2.commands.staff.StaffChat;
import me.caledonian.hybridsmpv2.discord.DiscordMessenger;
import me.caledonian.hybridsmpv2.utils.Data;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.JDA;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ChatFormatting implements Listener {
    private JavaPlugin plugin;
    JDA bot;
    public ChatFormatting(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }
    String prefix = Files.msgs.getString("prefix");

    // Normal
    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        // Staff Chat
        if(StaffChat.sc.contains(p)){
            sendStaffChat(e.getMessage(), p, bot);
            e.setCancelled(true);
            return;
        }

        // Chat Muted
        if(Data.get().getBoolean("chat.toggle")){
            if(!p.hasPermission(Files.perms.getString("admin.bypass-chat"))){
                p.sendMessage(Utils.chat(Files.msgs.getString("chat.chat-disabled").replace("%prefix%", Files.msgs.getString("prefix"))));
                e.setCancelled(true);
                return;
            }
        }

        // Actual Chat
        if (Files.config.getBoolean("chat-formatting.enable") == true) {
            e.setCancelled(true);

            // Anti swear
            if(!p.hasPermission(Files.perms.getString("staff"))){
                String message = e.getMessage();
                String[] msg = message.split(" ");
                List<String> blocked = Files.word.getStringList("blocked");
                for(String y : msg){
                    for (String x : blocked) {
                        if(y.equalsIgnoreCase(x)){
                            p.sendMessage(Utils.chat(Files.msgs.getString("chat.blocked-msg").replace("%prefix%", prefix).replace("%word%", x)));
                            String msg2 = Utils.chat(message).replace(x, "&c&n'"+x+"'&r");
                            Utils.sendLog(Files.msgs.getString("logs.blocked-word").replace("%msg%", msg2), Files.perms.getString("logs.blocked-word"), p);
                            return;
                        }
                    }
                }
            }

            // Chat Formatting
            String chat = PlaceholderAPI.setPlaceholders(p, Utils.chat(Files.config.getString("chat-formatting.format").replace("%player%", p.getName().replace("%displayname%", p.getDisplayName()))));
            TextComponent format = new TextComponent(chat);
            TextComponent msg2 = new TextComponent(Utils.chat(e.getMessage()));
            format.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, Utils.chat(Files.config.getString("chat-formatting.hover").replace("%player%", p.getName())))).create()));
            format.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, PlaceholderAPI.setPlaceholders(p, Files.config.getString("chat-formatting.click.command").replace("%player%", p.getName()))));

            for (Player player : Bukkit.getOnlinePlayers()) {
                if(!GlobalChat.toggled.contains(player)){
                    player.spigot().sendMessage(format, msg2);
                }
            }
            DiscordMessenger.chat(e.getPlayer(), e.getMessage(), bot);
        }
    }

    public static void sendStaffChat(String msg, Player p, JDA bot){

        String chat = PlaceholderAPI.setPlaceholders(p, Utils.chat(Utils.chat(Files.msgs.getString("staffchat.format").replace("%player%", p.getName()).replace("%server%", Files.config.getString("server-name")).replace("%displayname%", p.getDisplayName()))));
        TextComponent format = new TextComponent(chat);
        TextComponent msg2 = new TextComponent(Utils.chat(msg));
        format.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, Utils.chat(Files.config.getString("chat-formatting.hover").replace("%player%", p.getName())))).create()));
        format.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, PlaceholderAPI.setPlaceholders(p, Files.config.getString("chat-formatting.click.command").replace("%player%", p.getName()))));
        DiscordMessenger.staffChat(p, msg, bot);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPermission(Files.perms.getString("admin.staffchat"))){
                if(!GlobalChat.toggled.contains(player)){
                    player.spigot().sendMessage(format, msg2);
                }
            }
        }
    }
}
