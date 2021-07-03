package me.caledonian.hybridsmpv2.commands.developer;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pack implements CommandHandler {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        Player p = (Player) sender;
        try{
            p.setResourcePack(args[0]);
        }catch (Exception ex){ex.printStackTrace();p.sendMessage(Utils.chat("&c&l(!) &cThere was an error whilst loading the &nresource pack&r. Please report this to the devs."));}
    }
}
