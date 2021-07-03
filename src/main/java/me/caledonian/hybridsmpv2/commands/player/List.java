package me.caledonian.hybridsmpv2.commands.player;

import me.caledonian.hybridsmpv2.commands.staff.Vanish;
import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List implements CommandHandler {
    // Perms
    String staff = Files.perms.getString("staff");
    String special = Files.perms.getString("ranks.special");


    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;

            // Ints
            int staffI = 0;
            int specialI = 0;
            int defI = 0;
            int total = 0;

            for(Player o : Bukkit.getOnlinePlayers()){
                if(o.hasPermission(staff)){
                    if(!Vanish.vanished.contains(o)){
                        staffI = staffI + 1;
                    }
                }else if(o.hasPermission(special)){
                    specialI = specialI + 1;
                }
            }

            defI = Bukkit.getOnlinePlayers().size() - staffI - specialI;
            total = Bukkit.getOnlinePlayers().size() - Vanish.vanished.size();

            msg(p, staffI, specialI, defI, total);
        }
    }

    static void msg(Player p, int staff, int special, int def, int total){
        java.util.List<String> help = Files.msgs.getStringList("list");
        for (String x : help) {
            p.sendMessage(Utils.chat(x).replace("%staff%", String.valueOf(staff)).replace("%special%", String.valueOf(special)).replace("%default%", String.valueOf(def)).replace("%total%", String.valueOf(total)));
        }
    }
}
