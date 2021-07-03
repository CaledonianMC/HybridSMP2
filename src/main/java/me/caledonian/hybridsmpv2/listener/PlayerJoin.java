package me.caledonian.hybridsmpv2.listener;

import me.caledonian.hybridsmpv2.commands.staff.Vanish;
import me.caledonian.hybridsmpv2.utils.Data;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class PlayerJoin implements Listener {
    private JavaPlugin plugin;
    JDA bot;
    TextChannel textChannel;
    public PlayerJoin(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
        textChannel = bot.getTextChannelById(761224851503448074L);
    }

    String prefix = Utils.chat(Files.msgs.getString("prefix"));

    // Discord
    @EventHandler
    public void discordJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        List<String> list = Files.msgs.getStringList("join-leave.join");
        if(p.hasPermission(Files.perms.getString("other.join"))){
            e.setJoinMessage(Utils.chat(list.get(new Random().nextInt(list.size()))).replace("%prefix%", prefix).replace("%player%", p.getName()));
        }else{e.setJoinMessage(null);}

        if(bot != null){
            Bukkit.getScheduler().runTaskLater(plugin, () ->{
            bot.getPresence().setActivity(Activity.watching(Files.discord.getString("setup.activity").replace("%num%", String.valueOf(Bukkit.getOnlinePlayers().size()))));
            }, 20L);
        }

        if(!Vanish.vanished.isEmpty()){
            for(Player o : Vanish.vanished){
                e.getPlayer().hidePlayer(plugin, o);
            }
        }

        // Data Creation
        String uuid = p.getUniqueId().toString();

        Data.get().set("players.%uuid%.name".replace("%uuid%", uuid), p.getName());
        Data.save();
    }
    @EventHandler
    public void discordLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        List<String> list = Files.msgs.getStringList("join-leave.leave");
        if(p.hasPermission(Files.perms.getString("other.leave"))){
            e.setQuitMessage(Utils.chat(list.get(new Random().nextInt(list.size()))).replace("%prefix%", prefix).replace("%player%", p.getName()));
        }else{e.setQuitMessage(null);}

        if(bot != null){
            Bukkit.getScheduler().runTaskLater(plugin, () ->{
                bot.getPresence().setActivity(Activity.watching(Files.discord.getString("setup.activity").replace("%num%", String.valueOf(Bukkit.getOnlinePlayers().size()))));
            }, 20L);
        }
    }
}
