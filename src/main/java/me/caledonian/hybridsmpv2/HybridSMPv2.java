package me.caledonian.hybridsmpv2;

import me.caledonian.hybridsmpv2.discord.Discord;
import me.caledonian.hybridsmpv2.discord.DiscordMessenger;
import me.caledonian.hybridsmpv2.managers.CommandManager;
import me.caledonian.hybridsmpv2.managers.PlaceholderHandler;
import me.caledonian.hybridsmpv2.utils.Data;
import me.caledonian.hybridsmpv2.utils.Files;
import me.caledonian.hybridsmpv2.utils.Logger;
import me.caledonian.hybridsmpv2.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class HybridSMPv2 extends JavaPlugin {

    HybridSMPv2 plugin;
    JDA bot;

    @Override
    public void onEnable() {
        plugin = this;

        Logger.log(Logger.LogLevel.OUTLINE, "&8&m-----------------------------------------------");
        Logger.log(Logger.LogLevel.OUTLINE, "");
        Logger.log(Logger.LogLevel.OUTLINE, "&b&l * &fEnabling &bHybridSMP &fv%ver% by &bCaledonian&f...".replace("%ver%", getDescription().getVersion()));
        Logger.log(Logger.LogLevel.OUTLINE, "&b&l * &fServer Version: %srv%".replace("%srv%", getServer().getVersion()));
        Logger.log(Logger.LogLevel.OUTLINE, "");
        Logger.log(Logger.LogLevel.OUTLINE, "&8&m-----------------------------------------------");

        // Loading Files
        Files.base(this);
        Data.setup();

        // Discord
        try{
            bot = JDABuilder.createDefault(Files.discord.getString("setup.bot-token")).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES).setMemberCachePolicy(MemberCachePolicy.ALL).build().awaitReady();
            Logger.log(Logger.LogLevel.ERROR, "HybridSMP connected to the discord bot token.");
        }catch (Exception e){
            Logger.log(Logger.LogLevel.ERROR, "HybridSMP could not load the discord bot. The token is probably invalid");
            getPluginLoader().disablePlugin(this);
            return;
        }

        Discord.setup(this, bot);
        System.out.println(bot);

        // Registering Events & Commands
        new CommandManager(this, bot);
        Bukkit.addRecipe(getRecipe());

        // Hooks
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceholderHandler().register();
        }else{Logger.log(Logger.LogLevel.ERROR, "Could not find PlaceholderAPI, external placeholders will be disabled.");}

        // Finally
        if(bot != null){DiscordMessenger.sendStatus("Online", Files.config.getString("server-name"), bot);}
        Logger.log(Logger.LogLevel.OUTLINE, "\n\n&b&l * &bHybridSync &fv%ver% by &bCaledonian &fis fully loaded and running.\n".replace("%ver%", getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        if(bot != null){DiscordMessenger.sendStatus("Offline", Files.config.getString("server-name"), bot);}

        // Finally
        Discord.shutdown(bot);
    }

    private ShapedRecipe getRecipe(){
        ItemStack item = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta meta = item.getItemMeta();

        meta.setCustomModelData(1234567);
        meta.setDisplayName(Utils.chat("&bCal Coin"));

        item.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "coin");
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("III", "IDG", "GGG");
        recipe.setIngredient('I', Material.IRON_NUGGET);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('G', Material.GOLD_NUGGET);
        return recipe;
    }
}
