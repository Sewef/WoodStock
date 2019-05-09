package com.sewef.woodstock;

import com.sewef.woodstock.init.WoodStockBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WoodStockEvent {
    
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();
        
        if (getHarvestLevel(stack, "axe", player, state) >= 0) {
            Block block = state.getBlock();
            
            if (block == Blocks.LOG || block == Blocks.LOG2) {
                IProperty axis = null, variant = null;
                
                for (IProperty<?> prop : state.getProperties().keySet()) {
                    if (prop.getName().equals("axis")) {
                        axis = prop;
                    }
                    
                    if (prop.getName().equals("variant")) {
                        variant = prop;
                    }
                }
                
                if (axis != null && variant != null) {
                    if (WoodStockBlocks.variants.contains(state.getValue(variant).toString())) {
                        int i = WoodStockBlocks.variants.indexOf(state.getValue(variant).toString());
                        
                        if (world.isRemote) {
                            player.swingArm(event.getHand());
                            world.playSound(player, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(pos, state);
                        }
                        
                        world.setBlockState(pos, WoodStockBlocks.strippedLogs.get(i).getDefaultState().withProperty(axis, state.getValue(axis)), 0b1011);
                        stack.damageItem(1, player);
                    }
                }
            }
        }
    }
}
