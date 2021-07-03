package me.caledonian.hybridsmpv2.commands.player;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Coords implements CommandHandler {
    String prefix = Utils.chat(Files.msgs.getString("prefix"));

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Integer x = p.getLocation().getBlockX();
            Integer y = p.getLocation().getBlockY();
            Integer z = p.getLocation().getBlockZ();
            Utils.broadcast(Utils.chat(Files.msgs.getString("other.cords")).replace("%x%", x.toString()).replace("%y%", y.toString()).replace("%z%", z.toString()).replace("%player%", p.getName()).replace("%prefix%", prefix));

        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
