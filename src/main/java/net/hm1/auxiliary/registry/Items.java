package net.hm1.auxiliary.registry;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.items.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Auxiliary.MOD_ID);
    public static final RegistryObject<Item>
        GUN_SCHEMATIC,
        AUXILIARITE_ALLOY,
        DESTRUCTION_CATALYST,
        STALWART_CRYSTAL,
        CATACLYSMIC_AMALGAMATION,
        CORE_ITEM,
        FULCALIGR,
        SENTOUGAHARA;

    static
    {
        AUXILIARITE_ALLOY = ITEMS.register("auxiliarite_alloy", AuxiliariteAlloyItem::new);
        CATACLYSMIC_AMALGAMATION = ITEMS.register("cataclysmic_amalgamation", CataclysmicAmalgamationItem::new);
        CORE_ITEM = ITEMS.register("core_item", CoreItem::new);
        GUN_SCHEMATIC = ITEMS.register("gun_schematic", GunSchematic::new);
        DESTRUCTION_CATALYST = ITEMS.register("destruction_catalyst", DestructionCatalystItem::new);
        FULCALIGR = ITEMS.register("fulcaligr", FulcaligrItem::new);
        SENTOUGAHARA = ITEMS.register("sentougahara", SentougaharaItem::new);
        STALWART_CRYSTAL = ITEMS.register("stalwart_crystal", StalwartCrystalItem::new);
    }

    public static void register(IEventBus event)
    {
        ITEMS.register(event);
    }
}












