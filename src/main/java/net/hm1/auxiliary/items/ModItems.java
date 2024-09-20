package net.hm1.auxiliary.items;

import net.hm1.auxiliary.Auxiliary;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Auxiliary.MOD_ID);

    public static void register(IEventBus e) {



        e.register(ITEMS);
    }

    public class GunItems
    {
        public enum GunTypes { HANDGUN, SUBMACHINE_GUN, SHOTGUN, RIFLE, SNIPER_RIFLE, MACHINE_GUN, SPECIAL }
        public static final String[] AllHandgunIds = { "ad", };
        public static final String[] AllSMGIds = { "ad", };
        public static final String[] AllSGIds = { "ad", };
        public static final String[] AllARIds = { "ad", };
        public static final String[] AllSRIds = { "ad", };
        public static final String[] AllMGIds = { "ad", };
        public static final String[] AllSpecialIds = { "ad", };

        public static String getRandomGunIdOf(GunTypes gunType)
        {
            return switch (gunType)
            {
                case HANDGUN -> getRandomHandgunId();
                case SUBMACHINE_GUN -> getRandomSMGId();
                case SHOTGUN -> getRandomSGId();
                case RIFLE -> getRandomARId();
                case SNIPER_RIFLE -> getRandomSRId();
                case MACHINE_GUN -> getRandomMGId();
                case SPECIAL -> getRandomSpecialId();
                default -> "invalid";
            };
        }

        public static String getRandomHandgunId()
        {
            return "m1911a1";
        }

        public static String getRandomSMGId()
        {
            return "m1911a1";
        }

        public static String getRandomSGId()
        {
            return "m1911a1";
        }

        public static String getRandomARId()
        {
            return "m1911a1";
        }

        public static String getRandomSRId()
        {
            return "m1911a1";
        }

        public static String getRandomMGId()
        {
            return "m1911a1";
        }

        public static String getRandomSpecialId()
        {
            return "m1911a1";
        }
    }

    public static final RegistryObject<Item>
        UNRESEARCHED_GUN_SCHEMATIC = ITEMS.register("unresearched_gun_schematic", UnresearchedGunSchematic::new),
        RESEARCHED_GUN_SCHEMATIC = ITEMS.register("researched_gun_schematic", ResearchedGunSchematic::new),
        AUXILIARITE_ALLOY = ITEMS.register("auxiliarite_alloy", AuxiliariteAlloyItem::new),
        DESTRUCTION_CATALYST = ITEMS.register("destruction_catalyst", DestructionCatalystItem::new),
        STALWART_CRYSTAL = ITEMS.register("stalwart_crystal", StalwartCrystalItem::new),
        CATACLYSMIC_AMALGAMATION = ITEMS.register("cataclysmic_amalgamation", CataclysmicAmalgamationItem::new),
        CORE_ITEM = ITEMS.register("core_item", CoreItem::new),
        FULCALIGR = ITEMS.register("fulcaligr", FulcaligrItem::new),
        SENTOUGAHARA = ITEMS.register("sentougahara", SentougaharaItem::new);
}












