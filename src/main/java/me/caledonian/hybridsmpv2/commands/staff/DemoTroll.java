package me.caledonian.hybridsmpv2.commands.staff;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DemoTroll implements CommandHandler {
    String prefix = Utils.chat(Files.msgs.getString("prefix"));

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission(Files.perms.getString("admin.demo"))){
                if(args.length == 1){
                    if(Bukkit.getPlayer(args[0]) != null){
                        Player t = Bukkit.getPlayer(args[0]);

                        PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(new PacketPlayOutGameStateChange.a(5), 0.0F);
                        ((CraftPlayer) t).getHandle().playerConnection.sendPacket(packet);

                        p.sendMessage(Utils.chat(Files.msgs.getString("demo.action")).replace("%player%", t.getName()));
                    }else{p.sendMessage(Utils.chat(Files.msgs.getString("player-not-found").replace("%prefix%", prefix).replace("%player%", args[0])));}
                }else{p.sendMessage(Utils.chat(Files.msgs.getString("demo.usage")));}
            }else{p.sendMessage(Utils.chat(Files.msgs.getString("no-permission").replace("%prefix%", prefix)));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
