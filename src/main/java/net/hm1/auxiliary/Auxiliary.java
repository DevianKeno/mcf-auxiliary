package net.hm1.auxiliary;

import com.mojang.logging.LogUtils;
import net.hm1.auxiliary.init.ModBlocks;
import net.hm1.auxiliary.init.ModCreativeModTabs;
import net.hm1.auxiliary.init.ModItems;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.hm1.auxiliary.setup.registry.ArsNouveauAuxiliary;
import net.hm1.auxiliary.init.ModVillagers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/*
 * Auxiliary Mod by deviankeno
 * Supporting logic for the Auxiliary Modpack.
 *
 * Shout out to @kaupenjoe and their tutorials!
 */
@Mod(Auxiliary.MOD_ID)
public class Auxiliary
{
    public static final String MOD_ID = "auxiliary";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Auxiliary()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        AuxiliaryConfig.register();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModVillagers.register(modEventBus);
        ArsNouveauAuxiliary.Registry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        //modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::postSetup);
    }

    private void setupAddons(final FMLCommonSetupEvent event)
    {
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    public void postSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ArsNouveauAuxiliary::postSetup);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //if (event.getTabKey() == CreativeModeTabs.)
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }

    public static final String[] ARMOR_PIECES = { "helmet", "chestplate", "leggings", "boots" };
    public static final String[] IRONS_SPELLBOOKS_ARMOR_SET = { "wandering_magician", "pumpkin", "netherite_mage", "pyromancer", "electromancer", "archevoker", "cultist", "cryomancer", "shadowwalker", "priest", "plagued" };
    public static final String[] ICEANDFIRE_DRAGON_SCALE_COLORS = { "red", "bronze", "green", "gray", "blue", "white", "sapphire", "silver" , "electric", "amythest", "copper", "black" };
    public static final String[] ICEANDFIRE_DRAGONSTEEL_SET = { "fire", "ice", "lightning" };
    public static final String[] IMMERSIVE_ARMORS_SET = { "robe", "divine" };
}
