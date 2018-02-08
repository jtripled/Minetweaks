package com.jtripled.minetweaks.modules;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.block.tileentity.SmeltEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.recipe.smelting.SmeltingRecipe;
import org.spongepowered.api.util.Direction;

/**
 *
 * @author jtripled
 */
public class Compressor
{
    public Compressor()
    {
        /* Coal recipes. */
        registerSmeltingRecipe(ItemTypes.COAL_BLOCK, ItemTypes.COAL, 9);
        
        /* Iron recipes. */
        registerSmeltingRecipe(ItemTypes.IRON_BLOCK, ItemTypes.IRON_INGOT, 9);
        
        /* Gold recipes. */
        registerSmeltingRecipe(ItemTypes.GOLD_BLOCK, ItemTypes.GOLD_INGOT, 9);
        
        /* Diamond recipes. */
        registerSmeltingRecipe(ItemTypes.DIAMOND_BLOCK, ItemTypes.DIAMOND, 9);
        
        /* Emerald recipes. */
        registerSmeltingRecipe(ItemTypes.EMERALD_BLOCK, ItemTypes.EMERALD, 9);
        
        /* Redstone recipes. */
        registerSmeltingRecipe(ItemTypes.REDSTONE_BLOCK, ItemTypes.REDSTONE, 9);
        
        /* Lapis recipes. */
        //registerSmeltingRecipe(ItemTypes.LAPIS_BLOCK, ItemTypes.DYE, 9);
        
        /* Quartz recipes. */
        registerSmeltingRecipe(ItemTypes.QUARTZ_BLOCK, ItemTypes.QUARTZ, 4);
        
        /* Glowstone recipes. */
        registerSmeltingRecipe(ItemTypes.GLOWSTONE, ItemTypes.GLOWSTONE_DUST, 4);
        
        /* Slime recipes. */
        registerSmeltingRecipe(ItemTypes.SLIME, ItemTypes.SLIME_BALL, 9);
        
        /* Nether wart recipes. */
        registerSmeltingRecipe(ItemTypes.NETHER_WART_BLOCK, ItemTypes.NETHER_WART, 9);
        
        /* Bone recipes. */
        //registerSmeltingRecipe(ItemTypes.BONE_BLOCK, ItemTypes.DYE, 9);
    }
    
    public void onSmeltFinish(SmeltEvent.Finish event)
    {
        
    }
    
    private static void registerSmeltingRecipe(ItemType input, ItemType output)
    {
        registerSmeltingRecipe(input, 1, output, 1);
    }
    
    private static void registerSmeltingRecipe(ItemType input, int inputCount, ItemType output)
    {
        registerSmeltingRecipe(input, inputCount, output, 1);
    }
    
    private static void registerSmeltingRecipe(ItemType input, ItemType output, int outputCount)
    {
        registerSmeltingRecipe(input, 1, output, outputCount);
    }
    
    private static void registerSmeltingRecipe(ItemType input, int inputCount, ItemType output, int outputCount)
    {
        registerSmeltingRecipe(ItemStack.of(input, inputCount), ItemStack.of(output, outputCount));
    }
    
    private static void registerSmeltingRecipe(ItemStack input, ItemStack output)
    {
        Sponge.getRegistry().getSmeltingRecipeRegistry().register(
                SmeltingRecipe.builder().ingredient(input).result(output).build());
    }
}
