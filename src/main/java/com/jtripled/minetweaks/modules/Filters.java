package com.jtripled.minetweaks.modules;

import java.util.Optional;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.block.tileentity.carrier.TileEntityCarrier;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 *
 * @author jtripled
 */
public class Filters
{
    @Listener
    public void onHopperPull(ChangeInventoryEvent.Pickup.Pre event)
    {
        if (!event.getTargetInventory().getName().get().equals("Item Hopper"))
            return;
        System.out.println("pull");
    }
    
    @Listener
    public void onHopperPush(ChangeInventoryEvent.Transfer.Pre event)
    {
        if (!event.getSourceInventory().getName().get().equals("Item Hopper"))
            return;
        System.out.println("push");
    }
    
    @Listener
    public void onHopperShiftRightClick(InteractBlockEvent.Secondary event, @First Player player)
    {
        if (!player.getOrNull(Keys.IS_SNEAKING))
            return;
        
        if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent())
            return;
        
        /* Get block position. */
        Optional<Location<World>> opPos = event.getTargetBlock().getLocation();
        if (!opPos.isPresent())
            return;
        Location<World> pos = opPos.get();
        
        if (!this.isHopper(pos))
            return;
        
        TileEntityCarrier carrier = (TileEntityCarrier) pos.getTileEntity().get();
        System.out.println("click");
    }
    
    public boolean isHopper(Location<World> pos)
    {
        Optional<TileEntity> opTile = pos.getTileEntity();
        if (opTile.isPresent())
            return opTile.get().getType().equals(TileEntityTypes.HOPPER);
        return false;
    }
}
