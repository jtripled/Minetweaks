package com.jtripled.minetweaks.modules;

import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.SmeltEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.recipe.smelting.SmeltingRecipe;
import org.spongepowered.api.item.recipe.smelting.SmeltingResult;

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
        registerSmeltingRecipe(ItemStack.builder().itemType(ItemTypes.IRON_INGOT).quantity(9).build().createSnapshot(), ItemStack.builder().itemType(ItemTypes.IRON_BLOCK).build().createSnapshot());
        
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
    
    @Listener
    public void onSmeltFinish(SmeltEvent.Finish event)
    {
        System.out.println(event.getSmeltedItems());
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
    
    private static void registerSmeltingRecipe(ItemStackSnapshot input, ItemStackSnapshot output)
    {
        Sponge.getRegistry().getSmeltingRecipeRegistry().register(new CompressRecipe(input, output));
    }
    
    public static class CompressRecipe implements SmeltingRecipe
    {
        private final ItemStackSnapshot ingredient;
        private final ItemStackSnapshot result;
        
        public CompressRecipe(ItemStackSnapshot ingredient, ItemStackSnapshot result)
        {
            this.ingredient = ingredient;
            this.result = result;
        }
        
        @Override
        public ItemStackSnapshot getExemplaryIngredient()
        {
            return this.ingredient;
        }

        @Override
        public boolean isValid(ItemStackSnapshot ingredient)
        {
            if (ingredient.getType() != this.ingredient.getType())
                return false;
            if (ingredient.getQuantity() < this.ingredient.getQuantity())
                return false;
            return true;
        }

        @Override
        public Optional<SmeltingResult> getResult(ItemStackSnapshot ingredient)
        {
            if (this.isValid(ingredient))
                return Optional.of(new SmeltingResult(this.getExemplaryResult(), 0.0));
            else
                return Optional.empty();
        }

        @Override
        public ItemStackSnapshot getExemplaryResult()
        {
            return this.result;
        }
    }
}
