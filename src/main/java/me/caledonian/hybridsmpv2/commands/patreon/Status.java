package me.caledonian.hybridsmpv2.commands.patreon;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Data;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Status implements CommandHandler {

    String prefix = Utils.chat(Files.msgs.getString("prefix"));

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            try{
                if(p.hasPermission(Files.perms.getString("patreon.status"))){
                    // Has Perm
                    if(args.length == 0){
                        //p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 0));
                        p.sendMessage(Utils.chat(Files.msgs.getString("patreon.status.status").replace("%prefix%", prefix).replace("%status%", Data.get().getString("players.%uuid%.status".replace("%uuid%", p.getUniqueId().toString())))));
                    }else{
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            sb.append(args[i] + " ");
                        }

                        if(sb.toString().length() >= 30){
                            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]& ");
                            if(pattern.matcher(sb.toString()).find()){
                                Data.get().set("players.%uuid%.status".replace("%uuid%", p.getUniqueId().toString()), sb.toString());
                                Data.save();
                                p.sendMessage(Utils.chat(Files.msgs.getString("patreon.status.set").replace("%prefix%", prefix).replace("%status%", Utils.chat(Data.get().getString("players.%uuid%.status".replace("%uuid%", p.getUniqueId().toString()))))));
                            }else{p.sendMessage(Utils.chat(Files.msgs.getString("patreon.status.error").replace("%prefix%", prefix)));}
                        }else{p.sendMessage(Utils.chat(Files.msgs.getString("patreon.status.error").replace("%prefix%", prefix)));}
                    }
                }else{p.sendMessage(Utils.chat(Files.msgs.getString("patreon.no-perm").replace("%prefix%", prefix)));}
            }catch (Exception ex){p.sendMessage(Utils.chat("&c&l(!) &cAn error occurred whilst parsing the command."));ex.printStackTrace();}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
