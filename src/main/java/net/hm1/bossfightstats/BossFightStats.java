package net.hm1.bossfightstats;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BossFightStats.MOD_ID)
public class BossFightStats
{
    public static final String MOD_ID = "bossfightstats";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BossFightStats()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BFSConfig.register();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
