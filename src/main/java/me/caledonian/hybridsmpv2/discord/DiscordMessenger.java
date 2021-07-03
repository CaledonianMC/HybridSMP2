package me.caledonian.hybridsmpv2.discord;

import me.caledonian.hybridsmpv2.commands.staff.Vanish;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;
import java.lang.annotation.Target;
import java.util.Locale;

public class DiscordMessenger {

    public static void sendStatus(String status, String server, JDA bot){
        try{
            String path = "status."+status.toLowerCase(Locale.ROOT);
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.log-status"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString(path + ".author.message"), null, Files.discord.getString(path + ".author.image"));
            eb.setColor(new Color(Files.discord.getInt(path + ".color.red"), Files.discord.getInt(path + ".color.green"), Files.discord.getInt(path + ".color.blue")));
            eb.addField(Files.discord.getString(path + ".server.title"), Files.discord.getString(path + ".server.info").replace("%server%", server), true);
            eb.addField(Files.discord.getString(path + ".status.title"), Files.discord.getString(path + ".status.info"), true);
            eb.setFooter(Files.discord.getString(path + ".footer.message"), Files.discord.getString(path + ".footer.image"));
            tc.sendMessage(eb.build()).queue();

        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending status info");}
    }

    public static void chat(Player p, String message, JDA bot){
        try{
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.server-chat"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("chat.format.author.message").replace("%player%", p.getName()), null, Files.discord.getString("chat.format.author.image").replace("%player%", p.getName()));
            eb.setColor(new Color(Files.discord.getInt("chat.format.color.red"), Files.discord.getInt("chat.format.color.green"), Files.discord.getInt("chat.format.color.blue")));
            eb.setDescription(message);
            tc.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending a chat message to discord");e.printStackTrace();}
    }

    public static void staffChat(Player p, String message, JDA bot){
        try{
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.log-staff"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("staff-chat.format.author.message").replace("%player%", p.getName()), null, Files.discord.getString("staff-chat.format.author.image").replace("%player%", p.getName()));
            eb.setColor(new Color(Files.discord.getInt("staff-chat.format.color.red"), Files.discord.getInt("staff-chat.format.color.green"), Files.discord.getInt("staff-chat.format.color.blue")));
            eb.setDescription(message);
            tc.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending a staff chat message to discord");e.printStackTrace();}
    }

    public static void vanish(Player p, Player t, JDA bot, String status){
        try{
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.log-staff"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("vanish.author.message"), null, Files.discord.getString("vanish.author.image"));
            eb.setColor(new Color(Files.discord.getInt("vanish.color.red"), Files.discord.getInt("vanish.color.green"),Files.discord.getInt("vanish.color.blue")));
            eb.addField(Files.discord.getString("vanish.server.title"), Files.discord.getString("vanish.server.info").replace("%server%", Files.config.getString("server-name")), true);
            eb.addField(Files.discord.getString("vanish.status.title"), Files.discord.getString("vanish.status.info").replace("%status%", status), true);
            eb.addField(Files.discord.getString("vanish.player.title"), Files.discord.getString("vanish.player.info").replace("%player%", p.getName()), true);
            eb.addField(Files.discord.getString("vanish.target.title"), Files.discord.getString("vanish.target.info").replace("%target%", t.getName()), true);
            eb.setFooter(Files.discord.getString("vanish.footer.message"), Files.discord.getString("vanish.footer.image"));
            tc.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending vanish info");}
    }

    public static void death(Player p, JDA bot, String cause){
        try{
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.server-chat"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("death.author.message"), null, Files.discord.getString("vanish.author.image"));
            eb.setColor(new Color(Files.discord.getInt("death.color.red"), Files.discord.getInt("vanish.color.green"),Files.discord.getInt("vanish.color.blue")));
            eb.addField(Files.discord.getString("death.player.title"), Files.discord.getString("death.player.info").replace("%player%", p.getName()), true);
            eb.addField(Files.discord.getString("death.server.title"), Files.discord.getString("death.server.info").replace("%server%", Files.config.getString("death-name")), true);
            eb.addField(Files.discord.getString("death.reason.title"), Files.discord.getString("death.reason.info").replace("%reason%", cause), true);
            eb.setFooter(Files.discord.getString("death.footer.message"), Files.discord.getString("death.footer.image"));
            tc.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending death info");}
    }

    public static void join(Player p, JDA bot){
        try{
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.server-chat"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("join.author.message"), null, Files.discord.getString("join.author.image"));
            eb.setColor(new Color(Files.discord.getInt("join.color.red"), Files.discord.getInt("join.color.green"),Files.discord.getInt("join.color.blue")));
            eb.addField(Files.discord.getString("join.player.title"), Files.discord.getString("join.player.info").replace("%player%", p.getName()), true);
            eb.addField(Files.discord.getString("join.server.title"), Files.discord.getString("join.server.info").replace("%server%", Files.config.getString("death-name")), true);
            eb.setFooter(Files.discord.getString("join.footer.message"), Files.discord.getString("join.footer.image"));
            eb.setThumbnail(Files.discord.getString("join.footer.message").replace("%player%", p.getName()));
            tc.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending join info");}
    }

    public static void leave(Player p, JDA bot){
        try{
            TextChannel tc = bot.getTextChannelById(Files.discord.getString("channels.server-chat"));
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("leave.author.message"), null, Files.discord.getString("leave.author.image"));
            eb.setColor(new Color(Files.discord.getInt("leave.color.red"), Files.discord.getInt("leave.color.green"),Files.discord.getInt("leave.color.blue")));
            eb.addField(Files.discord.getString("leave.player.title"), Files.discord.getString("leave.player.info").replace("%player%", p.getName()), true);
            eb.addField(Files.discord.getString("leave.server.title"), Files.discord.getString("leave.server.info").replace("%server%", Files.config.getString("death-name")), true);
            eb.setFooter(Files.discord.getString("leave.footer.message"), Files.discord.getString("leave.footer.image"));
            eb.setThumbnail(Files.discord.getString("leave.footer.message").replace("%player%", p.getName()));
            tc.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending leave info");}
    }

    public static void onlineCmd(JDA bot, TextChannel textChannel){
        try{
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(Files.discord.getString("online.author.message"), null, Files.discord.getString("online.author.image"));
            eb.setColor(new Color(Files.discord.getInt("online.color.red"), Files.discord.getInt("online.color.green"),Files.discord.getInt("leave.color.blue")));
            eb.addField(Files.discord.getString("online.server.title"), Files.discord.getString("online.server.info").replace("%server%", Files.config.getString("server-name")), true);
            int online = Bukkit.getOnlinePlayers().size() - Vanish.vanished.size();
            eb.addField(Files.discord.getString("online.total.title"), Files.discord.getString("online.total.info").replace("%online%", String.valueOf(online)), true);
            StringBuilder sb = new StringBuilder();
            for(Player p : Bukkit.getOnlinePlayers()){
                if(!Vanish.vanished.contains(p)){sb.append(p.getName());}
            }
            eb.addField(Files.discord.getString("online.players.title"), Files.discord.getString("online.players.info").replace("%players%", sb.toString()), true);
            eb.setFooter(Files.discord.getString("online.footer.message"), Files.discord.getString("online.footer.image"));
            textChannel.sendMessage(eb.build()).queue();
        }catch (Exception e){Logger.log(Logger.LogLevel.ERROR, "HybridSMP encountered an error whilst sending online info");e.printStackTrace();}
    }
}
