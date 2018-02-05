package com.jtripled.minetweaks;

import com.google.inject.Inject;
import com.jtripled.minetweaks.modules.Caffeine;
import com.jtripled.minetweaks.modules.WorldMode;
import com.jtripled.minetweaks.modules.NoPortals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

/**
 *
 * @author jtripled
 */
@Plugin(
    id = "minetweaks",
    name = "Minetweaks",
    version = "dev",
    description = "Minor tweaks for Minecraft.",
    authors = "jtripled",
    url = "",
    dependencies = {}
)
public class Minetweaks
{
    public static Minetweaks INSTANCE;
    
    @Inject
    private Logger logger;

    @Inject
    private PluginContainer pluginContainer;
    
    @Inject @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader <CommentedConfigurationNode> loader;
    
    @Inject @DefaultConfig(sharedRoot = true)
    private Path path;

    private ConfigurationNode rootNode;

    @Listener
    public void onPreInitialization(GamePreInitializationEvent event) throws IOException
    {
        INSTANCE = this;
        loadConfig();
    }
    
    @Listener
    public void onReload(GameReloadEvent event) throws IOException
    {
        loadConfig();
    }
    
    private void loadConfig() throws IOException
    {
        if (Files.notExists(path))
        {
            Sponge.getAssetManager().getAsset(this, "minetweaks.conf").get().copyToFile(path);
        }
        rootNode = loader.load();
        Asset asset = Sponge.getAssetManager().getAsset(this, "minetweaks.conf").get();
        rootNode.mergeValuesFrom(HoconConfigurationLoader.builder().setURL(asset.getUrl()).build().load());
        loader.save(rootNode);
    }
    
    @Listener
    public void onInitialization(GameInitializationEvent event)
    {
        if (rootNode.getNode("caffeine").getBoolean())
        {
            Sponge.getEventManager().registerListeners(this, new Caffeine());
        }
        if (rootNode.getNode("noportals").getBoolean())
        {
            Sponge.getEventManager().registerListeners(this, new NoPortals());
        }
        if (rootNode.getNode("worldmode").getBoolean())
        {
            Sponge.getEventManager().registerListeners(this, new WorldMode());
        }
    }
    
    public static Minetweaks getInstance()
    {
        return INSTANCE;
    }
    
    public static Logger getLogger()
    {
        return INSTANCE.logger;
    }
    
    public static PluginContainer getContainer()
    {
        return INSTANCE.pluginContainer;
    }
}
