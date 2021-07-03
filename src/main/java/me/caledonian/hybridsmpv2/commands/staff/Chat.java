package me.caledonian.hybridsmpv2.commands.staff;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Data;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class Chat implements CommandHandler, Listener {
    String prefix = Files.msgs.getString("prefix");

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission(Files.perms.getString("admin.chat"))){
                if(args.length == 0){
                    usage(p);
                }else {
                    if(args[0].equalsIgnoreCase("toggle")){
                        // Toggle Chat Methods
                        boolean toggled = Data.get().getBoolean("chat.toggle");
                        if(toggled){
                            Data.get().set("chat.toggle", false);
                            Data.save();
                            Utils.broadcast(Utils.chat(Files.msgs.getString("chat.toggle").replace("%prefix%", prefix).replace("%status%", "ENABLED")));

                        }else{
                            Data.get().set("chat.toggle", true);
                            Data.save();
                            Utils.broadcast(Utils.chat(Files.msgs.getString("chat.toggle").replace("%prefix%", prefix).replace("%status%", "DISABLED")));
                        }


                    }else if(args[0].equalsIgnoreCase("clear")){
                        // Clear Chat Methods
                        for(int i = 0; i < 100; ++i){
                            Utils.broadcast(Utils.chat("&f "));
                        }
                        Bukkit.broadcastMessage(Utils.chat(Files.msgs.getString("chat.clear")));

                    }else if(args[0].equalsIgnoreCase("slow")){
                        // Chat Slow Methods
                    }else if(args[0].equalsIgnoreCase("cmd")){

                    }else{usage(p);}
                }
            }else{p.sendMessage(Utils.chat(Files.msgs.getString("no-permission").replace("%prefix%", prefix)));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }

    static void usage(Player p){
        List<String> help = Files.msgs.getStringList("chat.usage");
        for (String x : help) {
            p.sendMessage(Utils.chat(x).replace("%prefix%", Files.msgs.getString("prefix")));
        }
    }

    // Chat Listener
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(Data.get().getBoolean("chat.toggle")){
            if(!p.hasPermission(Files.perms.getString("admin.bypass-chat"))){
                p.sendMessage(Utils.chat(Files.msgs.getString("admin.chat-disabled").replace("%prefix%", prefix)));
                e.setCancelled(true);

            }else{return;}
        }
    }
}
