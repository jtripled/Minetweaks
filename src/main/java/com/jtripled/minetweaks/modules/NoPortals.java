package com.jtripled.minetweaks.modules;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
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
    
    @Listener
    public void onBlockChange(ChangeBlockEvent.Place event)
    {
        for (Transaction<BlockSnapshot> block : event.getTransactions())
        {
            if (block.getFinal().getState().getType().equals(BlockTypes.PORTAL))
            {
                block.setCustom(block.getOriginal());
            }
        }
    }
}
