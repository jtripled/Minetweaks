package com.jtripled.minetweaks.modules;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.world.storage.WorldProperties;

/**
 *
 * @author jtripled
 */
public class WorldMode
{
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
        
        String override = null;
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
