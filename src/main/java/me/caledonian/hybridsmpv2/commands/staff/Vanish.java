package me.caledonian.hybridsmpv2.commands.staff;

import me.caledonian.hybridsmpv2.discord.DiscordMessenger;
import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Vanish implements CommandHandler {
    public static ArrayList<Player> vanished = new ArrayList<>();
    private static JavaPlugin plugin;
    JDA bot;
    public Vanish(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }
    static String prefix = Utils.chat(Files.msgs.getString("prefix"));

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission(Files.perms.getString("admin.vanish.self"))) {
                if (args.length == 0) {
                    // Enabling Self
                    toggleVanish(p);
                    p.sendMessage(Utils.chat(Files.msgs.getString("vanish.self").replace("%prefix%", prefix).replace("%status%", status(p))));
                    Utils.sendLog(Files.msgs.getString("logs.vanish.self").replace("%status%", status(p)), Files.perms.getString("logs.vanish"), p);
                    DiscordMessenger.vanish(p, p, bot, status(p));

                } else if (args.length == 1) {
                    // Enabling Other
                    if (Bukkit.getPlayer(args[0]) != null) {
                        // Player online
                        Player t = Bukkit.getPlayer(args[0]);
                        toggleVanish(t);
                        p.sendMessage(Utils.chat(Files.msgs.getString("vanish.other").replace("%prefix%", prefix).replace("%status%", status(p)).replace("%target%", t.getName())));
                        Utils.sendLog(Files.msgs.getString("logs.vanish.other").replace("%status%", status(p).replace("%target%", t.getName())), Files.perms.getString("logs.vanish"), p);
                        DiscordMessenger.vanish(p, t, bot, status(t));
                    } else {
                        p.sendMessage(Utils.chat(Files.msgs.getString("player-not-found").replace("%prefix%", prefix).replace("%player%", args[0])));
                    }
                } else {
                    usage(p);
                }


            }else{p.sendMessage(Utils.chat(Files.msgs.getString("no-permission").replace("%prefix%", prefix)));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("core.console-error")));}
    }

    static void usage(Player p){
        List<String> help = Files.msgs.getStringList("vanish.usage.help");
        for (String x : help) {
            p.sendMessage(Utils.chat(x).replace("%prefix%", Files.msgs.getString("prefix")));
        }
    }

    static void toggleVanish(Player p){
        if(vanished.contains(p)){
            vanished.remove(p);
            for(Player o : Bukkit.getOnlinePlayers()){
                o.showPlayer(plugin, p);
            }
        }else{
            vanished.add(p);
            for (Player player : Bukkit.getOnlinePlayers()){
                if(!player.hasPermission(Files.perms.getString("admin.vanish.see"))){player.hidePlayer(p);}
            }
        }
    }

    static String status(Player p){
        if(vanished.contains(p)){return "Enabled";}else{return "Disabled";}
    }
}
