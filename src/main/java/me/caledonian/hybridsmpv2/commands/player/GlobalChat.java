package me.caledonian.hybridsmpv2.commands.player;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GlobalChat implements CommandHandler {
    public static ArrayList<Player> toggled = new ArrayList<>();
    String prefix = Utils.chat(Files.msgs.getString("prefix"));

    String status;

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;

            if(toggled.contains(p)){
                // Chat Disabled
                toggled.remove(p);
                status = "Enabled";
                p.sendMessage(Utils.chat(Files.msgs.getString("chat.globalchat").replace("%prefix%", prefix).replace("%status%", this.status)));
            }else{
                // Chat Enabled
                toggled.add(p);
                status = "Disabled";
                p.sendMessage(Utils.chat(Files.msgs.getString("chat.globalchat").replace("%prefix%", prefix).replace("%status%", this.status)));
            }

        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
