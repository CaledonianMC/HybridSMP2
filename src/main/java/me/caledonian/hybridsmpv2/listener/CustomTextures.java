package me.caledonian.hybridsmpv2.listener;

import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class CustomTextures implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        try{
            // GUI
            e.getPlayer().setResourcePack(Files.config.getString("resourcepack"));
        }catch (Exception ex){ex.printStackTrace();e.getPlayer().sendMessage(Utils.chat("&c&l(!) There was an error whilst loading the &nresource pack&r. Please report this to the devs."));}
    }

    //@EventHandler
    public void statusEvent(PlayerResourcePackStatusEvent e){
        if(e.getPlayer().hasPermission("hybridcore.texture.bypass")){
            if(e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED){
                e.getPlayer().kickPlayer("\n&c&lRESOURCE PACK ERROR\n&f \n&7&oYou were kicked because you &f&omust use &7&othe resource pack.");
            }else if(e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
                e.getPlayer().kickPlayer("\n&c&lRESOURCE PACK ERROR\n&f \n&7&oYou were kicked because the &f&oresource pack failed to download");
            }
        }
    }
}
