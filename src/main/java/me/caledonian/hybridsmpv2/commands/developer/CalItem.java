package me.caledonian.hybridsmpv2.commands.developer;

import me.caledonian.hybridsmpv2.managers.CommandHandler;
import me.caledonian.hybridsmpv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CalItem implements CommandHandler {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            try{
                ItemStack itemStack = new ItemStack(Material.getMaterial(args[0]), 1, (byte) 430);
                ItemMeta meta = itemStack.getItemMeta();
                meta.setCustomModelData(1234567);

                itemStack.setItemMeta(meta);
                p.getInventory().addItem(itemStack);


            }catch (Exception ex){ex.printStackTrace();p.sendMessage(Utils.chat("&c&l(!) &cThere was an error whilst giving the &nitem&r. Please report this to the devs."));}
        }
    }
}
