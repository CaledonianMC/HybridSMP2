package me.caledonian.hybridsmpv2.commands.player;

import me.caledonian.hybridsmpv2.commands.staff.Vanish;
import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffList implements CommandHandler {
    public static ArrayList<Player> hide = new ArrayList<>();
    String prefix = Files.msgs.getString("prefix");


    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1 && args[0].equalsIgnoreCase("hide") && p.hasPermission(Files.perms.getString("admin.stafflist"))){
                if(hide.contains(p)){
                    String status = Files.msgs.getString("names.showed");
                    p.sendMessage(Utils.chat(Files.msgs.getString("stafflist.hide").replace("%prefix%", prefix).replace("%status%", status)));
                    hide.remove(p);

                    Utils.sendLog(Files.msgs.getString("logs.staff-hide").replace("%status%", status), Files.perms.getString("logs.staff-hide"), p);
                }else{
                    String status = Files.msgs.getString("names.hid");
                    p.sendMessage(Utils.chat(Files.msgs.getString("stafflist.hide").replace("%prefix%", prefix).replace("%status%", status)));
                    hide.add(p);

                    Utils.sendLog(Files.msgs.getString("logs.staff-hide").replace("%status%", status), Files.perms.getString("logs.staff-hide"), p);
                }

            }else{
                final List<Player> staffs = new ArrayList<Player>();
                for(Player o : Bukkit.getOnlinePlayers()){
                    if(o.hasPermission(Files.perms.getString("staff")) && !Vanish.vanished.contains(o) && !hide.contains(o)){
                        staffs.add(o);
                    }
                }

                // Handle Staff
                if(!staffs.isEmpty()){
                    // Header
                    for (String header1 : Files.msgs.getStringList("stafflist.format.header")){
                        header1 = header1.replace("&", "§");
                        p.sendMessage(header1);
                    }
                    // List
                    for(int i = 0; i != staffs.size(); ++i) {
                        final Player player = staffs.get(i);
                        final TextComponent tp = new TextComponent(Utils.chat(Files.msgs.getString("stafflist.staff").replace("%player%", player.getName()).replace("%server%",
                                Files.config.getString("server-name"))));

                        String hover1 = PlaceholderAPI.setPlaceholders(player, Files.config.getString("staff.stafflist.events.hover"));
                        hover1 = hover1.replace("&", "§");
                        hover1 = hover1.replace("%player%", player.getName());
                        hover1 = hover1.replace("%server%", Files.config.getString("server-name"));
                        tp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover1).create()));
                        tp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Files.config.getString("staff.stafflist.events.click").replace("%player%", p.getName())));
                        p.spigot().sendMessage(tp);
                    }

                    for (String footer1 : Files.msgs.getStringList("stafflist.format.footer")){
                        footer1 = footer1.replace("&", "§");
                        p.sendMessage(footer1);
                    }
                }else{p.sendMessage(Utils.chat(Files.msgs.getString("stafflist.format.empty")).replace("%prefix%", Utils.chat(Files.msgs.getString("prefix"))));}
            }


        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
