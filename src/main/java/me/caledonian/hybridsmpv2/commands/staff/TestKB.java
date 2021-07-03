package me.caledonian.hybridsmpv2.commands.staff;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;

public class TestKB implements CommandHandler {
    String prefix = Files.msgs.getString("prefix");
    private static JavaPlugin plugin;
    JDA bot;
    public TestKB(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission(Files.perms.getString("admin.testkb"))){
                if(args.length == 0) {
                    p.sendMessage(Utils.chat(Files.msgs.getString("testkb.usage").replace("%prefix%", prefix)));
                }else{
                    if(Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()){
                        Player t = Bukkit.getPlayer(args[0]);
                        p.sendMessage(Utils.chat(Files.msgs.getString("testkb.start").replace("%prefix%", prefix).replace("%player%", t.getName())));

                        // Vectors
                        Location locO = t.getLocation();

                        Vector v = t.getLocation().getDirection();
                        v.setX(1);
                        v.setY(1);
                        t.setVelocity(v);
                        Bukkit.getScheduler().runTaskLater(plugin, () ->{
                            p.sendMessage(Utils.chat(Files.msgs.getString("testkb.end").replace("%prefix%", prefix).replace("%player%", t.getName())));
                            t.teleport(locO);
                        }, 10L);

                    }
                }
            }else{p.sendMessage(Utils.chat(Files.msgs.getString("no-permission")));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("console-error")));}
    }
}
