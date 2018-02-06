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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.world.storage.WorldProperties;

/**
 *
 * @author jtripled
 */
public class WorldMode
{
    private ConfigurationNode rootNode;
    
    public WorldMode() throws IOException
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
        Path configPath = Minetweaks.getConfigDirectory().resolve("worldmode.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(configPath).build();
        if (Files.notExists(configPath))
        {
            Sponge.getAssetManager().getAsset(Minetweaks.INSTANCE, "worldmode.conf").get().copyToDirectory(Minetweaks.getConfigDirectory());
        }
        rootNode = loader.load();
        Asset asset = Sponge.getAssetManager().getAsset(Minetweaks.INSTANCE, "worldmode.conf").get();
        rootNode.mergeValuesFrom(loader.load());
        loader.save(rootNode);
    }
    
    @Listener
    public void onPlayerTeleport(MoveEntityEvent.Teleport event, @Getter("getTargetEntity") Player player)
    {
        if (player.hasPermission("minetweaks.worldmode.bypass"))
        {
            return;
        }
        
        WorldProperties from = event.getFromTransform().getExtent().getProperties();
        WorldProperties to = event.getToTransform().getExtent().getProperties();

        if (from.equals(to))
        {
            return;
        }
        
        String override = rootNode.getNode("world-override", to.getWorldName()).getString();
        GameMode mode;
        
        if (override != null)
        {
            switch (override.toLowerCase())
            {
                case "adventure": mode = GameModes.ADVENTURE; break;
                case "survival": mode = GameModes.SURVIVAL; break;
                case "creative": mode = GameModes.CREATIVE; break;
                default: mode = GameModes.SURVIVAL; break;
            }
        }
        else
        {
            mode = to.getGameMode();
        }
        
        if (mode == player.require(Keys.GAME_MODE))
        {
            return;
        }
        
        player.offer(Keys.GAME_MODE, mode);
    }
}
