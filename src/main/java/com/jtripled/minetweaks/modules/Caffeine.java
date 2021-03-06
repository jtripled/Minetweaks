package com.jtripled.minetweaks.modules;

import com.jtripled.minetweaks.Minetweaks;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.util.RespawnLocation;

/**
 *
 * @author jtripled
 */
public class Caffeine
{
    private ConfigurationNode rootNode;
    
    public Caffeine() throws IOException
    {
        loadConfig();
    }
    
    @Listener
    public void onReload(GameReloadEvent event) throws IOException
    {
        loadConfig();
    }
    
    private void loadConfig() throws IOException
    {
        Path configPath = Minetweaks.getConfigDirectory().resolve("caffeine.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configPath).build();
        if (Files.notExists(configPath))
        {
            Sponge.getAssetManager().getAsset(Minetweaks.INSTANCE, "caffeine.conf").get().copyToDirectory(Minetweaks.getConfigDirectory());
        }
        rootNode = loader.load();
        Asset asset = Sponge.getAssetManager().getAsset(Minetweaks.INSTANCE, "caffeine.conf").get();
        rootNode.mergeValuesFrom(loader.load());
        loader.save(rootNode);
    }
    
    @Listener(order = Order.LAST)
    public void onPlayerInteract(InteractBlockEvent.Secondary event, @First Player player)
    {
        BlockSnapshot block = event.getTargetBlock();
        if (block.getState().getId().contains("minecraft:bed"))
        {
            /* Cancel the event. */
            event.setCancelled(true);
            
            /* Register spawn point.*/
            if (rootNode.getNode("enable-spawn").getBoolean())
            {
                /* Update spawn point. */
                RespawnLocation location = RespawnLocation.builder().position(block.getPosition().toDouble()).world(block.getWorldUniqueId()).forceSpawn(false).build();
                
                /* Notify player of spawn point change. */
                Text text = Text.builder("You have set your spawn point").color(TextColors.YELLOW).build();
                Title title = Title.builder().actionBar(text).stay(80).fadeIn(5).fadeOut(5).build();
                player.sendTitle(title);
                
                /* Log spawn point change. */
                Minetweaks.getLogger().info("Player " + player.getName() + " (UUID=" + player.getUniqueId() + ") has set their spawn point to " + location);
            }
        }
    }
}
