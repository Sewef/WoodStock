package com.sewef.woodstock;

import com.sewef.woodstock.init.WoodStockBlocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = WoodStock.MODID, version = WoodStock.VERSION)
public class WoodStock
{
	public static final String MODID = "woodstock";
	public static final String VERSION = "1.3";

    private static Logger logger;
        
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        WoodStockBlocks.init();
    }
        
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
        MinecraftForge.EVENT_BUS.register(new WoodStockEvent());
        logger.info("Making hot movies about trees...");
	}
}