package me.caledonian.hybridsmpv2.listener;

import me.caledonian.hybridsmpv2.discord.DiscordMessenger;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class OtherListener implements Listener {

    private JavaPlugin plugin;
    JDA bot;
    public OtherListener(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }

    // Death Discord
    public void onDeathDiscord(PlayerDeathEvent e){
        Player p = e.getEntity();

        DiscordMessenger.death(p, bot, e.getDeathMessage());
    }

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent e){
        if(Files.config.getBoolean("other.block-end")){
            Bukkit.broadcastMessage("changed world " + e.getFrom());
            Player p = e.getPlayer();
            if(p.getWorld().getName().equalsIgnoreCase("world_the_end")){
                p.teleport(new Location(Bukkit.getServer().getWorld("world_the_end"), -764, 88, -349));
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        DiscordMessenger.join(e.getPlayer(), bot);
    }
    @EventHandler
    public void leave(PlayerQuitEvent e){
        DiscordMessenger.leave(e.getPlayer(), bot);
    }


    @EventHandler
    public void versions(PlayerJoinEvent e){
        Player p = e.getPlayer();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
            if(plugin.getDescription().getVersion().contains("dev")){
                p.sendMessage(Utils.chat("&c*&8&m-----------&c*&8&m------------------&c*&8&m-----------&c*"));
                p.sendMessage(Utils.chat("&f      &fYour using a &bDeveloper &fVersion"));
                p.sendMessage(Utils.chat("&f "));
                p.sendMessage(Utils.chat("&f * &cVersion&8: &f%version%").replace("%version%", Utils.ver));
                p.sendMessage(Utils.chat("&f * &cReason&8: &fDevelopment builds are generally unstable and"));
                p.sendMessage(Utils.chat("&f * &cReason&8: &fincomplete. Do you know what your doing?"));
                p.sendMessage(Utils.chat("&f "));
                p.sendMessage(Utils.chat("&c*&8&m-----------&c*&8&m------------------&c*&8&m-----------&c*"));
            }else if(plugin.getDescription().getVersion().contains("pre")){
                p.sendMessage(Utils.chat("&c*&8&m-----------&c*&8&m------------------&c*&8&m-----------&c*"));
                p.sendMessage(Utils.chat("&f      &fYour using a &bPre Release &fVersion"));
                p.sendMessage(Utils.chat("&f "));
                p.sendMessage(Utils.chat("&f * &cVersion&8: &f%version%").replace("%version%", Utils.ver));
                p.sendMessage(Utils.chat("&f * &cReason&8: &fPre Release versions aren't final and "));
                p.sendMessage(Utils.chat("&f * &cReason&8: &fcould be buggy. Please report any bugs."));
                p.sendMessage(Utils.chat("&f "));
                p.sendMessage(Utils.chat("&c*&8&m-----------&c*&8&m------------------&c*&8&m-----------&c*"));
            }
        }, 43L);
    }
}
