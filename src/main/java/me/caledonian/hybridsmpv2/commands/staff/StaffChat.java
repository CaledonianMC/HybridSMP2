package me.caledonian.hybridsmpv2.commands.staff;

import me.caledonian.hybridsmpv2.listener.ChatFormatting;
import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class StaffChat implements CommandHandler {
    private static JavaPlugin plugin;
    JDA bot;
    public StaffChat(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }

    public static ArrayList<Player> sc = new ArrayList<>();
    String prefix = Files.msgs.getString("prefix");

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission(Files.perms.getString("admin.staffchat"))){
                if(args.length == 0){
                    if(sc.contains(p)){
                        // Disable Staff Chat
                        sc.remove(p);
                        p.sendMessage(Utils.chat(Files.msgs.getString("staffchat.args.disable").replace("%prefix%", prefix)));
                    }else{
                        // Enable Staff Chat
                        sc.add(p);
                        p.sendMessage(Utils.chat(Files.msgs.getString("staffchat.args.enable").replace("%prefix%", prefix)));
                    }
                }else{
                    StringBuilder sb = new StringBuilder();
                    for(String s : args){
                        sb.append(s + " ");
                    }

                    ChatFormatting.sendStaffChat(sb.toString(), p, bot);
                }
            }else{p.sendMessage(Utils.chat(Files.msgs.getString("no-permission").replace("%prefix%", prefix)));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
