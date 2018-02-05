package com.jtripled.minetweaks.modules;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;

/**
 *
 * @author jtripled
 */
public class Caffeine
{
    @Listener
    public void onPlayerInteract(InteractBlockEvent.Secondary event, @First Player player)
    {
        BlockSnapshot block = event.getTargetBlock();
        if (block.getState().getId().contains("minecraft:bed"))
        {
            /* Cancel the event. */
            event.setCancelled(true);
        }
    }
}
