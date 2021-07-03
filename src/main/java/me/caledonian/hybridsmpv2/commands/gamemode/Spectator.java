package me.caledonian.hybridsmpv2.commands.gamemode;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spectator implements CommandHandler {
    String prefix = Utils.chat(Files.msgs.getString("prefix"));

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            String cmd = "gmsp";
            String gamemode = "spectator";
            Player p = (Player) sender;
            if(args.length == 0){
                if(p.hasPermission(Files.perms.getString("spectator.self"))){
                    p.sendMessage(Utils.chat(Files.msgs.getString("gamemodes.self").replace("%player%", p.getName()).replace("%prefix%", prefix)).replace("%gamemode%", gamemode));
                    p.setGameMode(GameMode.SPECTATOR);

                    // Logger
                    Utils.sendLog(Files.msgs.getString("logs.gamemode.self").replace("%prefix%", prefix).replace("%gamemode%", gamemode), Files.perms.getString("logs.gamemode"), p);
                }else{p.sendMessage(Utils.chat(Files.msgs.getString("no-permission")));}
            }else if(args.length == 1){
                // Other
                if(Bukkit.getPlayer(args[0]) != null){
                    // Player not null
                    Player t = Bukkit.getPlayer(args[0]);

                    p.sendMessage(Utils.chat(Files.msgs.getString("gamemodes.sender").replace("%player%", p.getName()).replace("%prefix%", prefix)).replace("%gamemode%", gamemode).replace("%target%", t.getName()));
                    t.setGameMode(GameMode.SPECTATOR);

                    // Logger
                    Utils.sendLog(Files.msgs.getString("logs.gamemode.other").replace("%prefix%", prefix).replace("%gamemode%", gamemode).replace("%target%", t.getName()), Files.perms.getString("logs.gamemode"), p);

                }else{p.sendMessage(Utils.chat(Files.msgs.getString("player-not-found").replace("%prefix%", prefix).replace("%player%", args[0])));}
            }else{p.sendMessage(Utils.chat(Files.msgs.getString("gamemodes.usage").replace("%prefix%", Files.msgs.getString("prefix")).replace("%command%", cmd)));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
