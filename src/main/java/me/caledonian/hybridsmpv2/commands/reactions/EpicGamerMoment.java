package me.caledonian.hybridsmpv2.commands.reactions;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EpicGamerMoment implements CommandHandler {
    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            String prefix = Files.msgs.getString("prefix");
            String player = p.getName();
            Bukkit.broadcastMessage(Utils.chat(Files.msgs.getString("reactions.epic-gamer-moment").replace("%prefix%", prefix).replace("%player%", player)));

        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
