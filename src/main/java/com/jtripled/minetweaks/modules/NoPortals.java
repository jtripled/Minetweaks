package com.jtripled.minetweaks.modules;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.ConstructPortalEvent;

/**
 *
 * @author jtripled
 */
public class NoPortals
{
    @Listener
    public void onPortalConstruct(ConstructPortalEvent event)
    {
        event.setCancelled(true);
    }
}
