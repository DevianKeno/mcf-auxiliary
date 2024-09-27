package net.hm1.auxiliary;

import com.mojang.logging.LogUtils;
import net.hm1.auxiliary.registry.*;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.hm1.auxiliary.setup.registry.ArsNouveauAuxiliary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
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
        AuxiliaryConfig.register();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        Items.register(modEventBus);
        Blocks.register(modEventBus);
        Enchantments.register(modEventBus);
        Villagers.register(modEventBus);
        ArsNouveauAuxiliary.Registry.register(modEventBus);
        CreativeTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
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
