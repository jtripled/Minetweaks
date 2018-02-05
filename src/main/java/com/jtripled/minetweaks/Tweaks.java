package com.jtripled.minetweaks;

import com.jtripled.minetweaks.modules.Caffeine;
import com.jtripled.minetweaks.modules.WorldMode;
import com.jtripled.minetweaks.modules.NoPortals;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

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
public class Tweaks
{
    @Listener
    public void onInitialization(GameInitializationEvent event)
    {
        Sponge.getEventManager().registerListeners(this, new Caffeine());
        Sponge.getEventManager().registerListeners(this, new NoPortals());
        Sponge.getEventManager().registerListeners(this, new WorldMode());
    }
}
