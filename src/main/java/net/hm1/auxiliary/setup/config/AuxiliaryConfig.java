package net.hm1.auxiliary.setup.config;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.armor.MagicArmor;
import net.hm1.auxiliary.setup.registry.GunsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.hm1.auxiliary.setup.registry.GunsRegistry.GunTypes.*;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AuxiliaryConfig
{
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static class Enchantments
    {
        public static ForgeConfigSpec.IntValue BALLISTICS_MAX_LEVEL;
        public static ForgeConfigSpec.DoubleValue BULLET_DAMAGE_PER_LEVEL;
        public static ForgeConfigSpec.DoubleValue SPELL_POWER_PER_LEVEL;
        public static ForgeConfigSpec.DoubleValue SPELL_HASTE_PER_LEVEL;
        public static ForgeConfigSpec.DoubleValue SPELL_RESIST_PER_LEVEL;
    }

    public static class MagicArmorConfig
    {
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> MAGIC_ARMOR_ITEMS;
        public static ForgeConfigSpec.BooleanValue MAGIC_ARMOR_IS_THREADABLE;
    }

    public static Map<GunsRegistry.GunTypes, ForgeConfigSpec.IntValue> GUNTYPE_SCHEMATIC_PRICES = new HashMap<>();
    private static final Map<GunsRegistry.GunTypes, Integer> DEFAULT_GUNTYPE_SCHEMATIC_PRICES = new HashMap<>();
    static {
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(HANDGUN, 99);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(SHOTGUN, 149);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(SUBMACHINE_GUN, 169);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(ASSAULT_RIFLE, 299);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(SNIPER_RIFLE, 499);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(DESIGNATED_MARKSMAN_RIFLE, 699);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(MACHINE_GUN, 999);
        DEFAULT_GUNTYPE_SCHEMATIC_PRICES.put(SPECIAL, 999);
    }
    public static ForgeConfigSpec.BooleanValue NO_ATTACHMENTS_ON_CRAFTED_GUNS;

    public static class Auxiliary
    {

    }

    public static ForgeConfigSpec.IntValue AUXI_WEAPON_DURABILITY;
    public static ForgeConfigSpec.DoubleValue AUXI_SPEED;
    public static ForgeConfigSpec.IntValue FULCALIGR_ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue FULCALIGR_ATTACK_SPEED;

    static
    {
        //region Server
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("server config");
        SERVER_BUILDER.push("magic armor settings");
            MagicArmorConfig.MAGIC_ARMOR_ITEMS = SERVER_BUILDER
                .comment("The list of all ids of armor tagged '#armors/magic'")
                .comment("This also affects which armors can be Threaded")
                .defineListAllowEmpty("items", MagicArmor.ALL_IDS, AuxiliaryConfig::isItemExists);
            MagicArmorConfig.MAGIC_ARMOR_IS_THREADABLE = SERVER_BUILDER
                .comment("Whether to make all magic armors Ars Nouveau Threadable")
                .define("magic_armor_is_threadable", true);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("gun schematic trade prices");
            NO_ATTACHMENTS_ON_CRAFTED_GUNS = SERVER_BUILDER
                .comment("Pointblank crafted guns have attachments when crafted. Set this to true to make no attachments on craft.")
                .define("no_attachment_on_crafted_guns", true);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("enchantments");
            Enchantments.BALLISTICS_MAX_LEVEL = SERVER_BUILDER
                .comment("Max level for Ballistics enchantment.")
                .defineInRange("ballistics_max_level", 5, 1, 32767);
            Enchantments.BULLET_DAMAGE_PER_LEVEL = SERVER_BUILDER
                .comment("Added Bullet Damage per Ballistics enchantment level.")
                .defineInRange("bullet_damage_per_level", 2.5f, 0f, 99f);
            Enchantments.SPELL_POWER_PER_LEVEL = SERVER_BUILDER
                .comment("Added Spell Power per enchantment level. ")
                    .defineInRange("spell_power_per_level", 0.05f, 0f, 99f);
            Enchantments.SPELL_HASTE_PER_LEVEL = SERVER_BUILDER
                .comment("Added Cast Time Reduction per enchantment level.")
                .defineInRange("spell_haste_per_level", 2f, 0f, 99f);
            Enchantments.SPELL_RESIST_PER_LEVEL = SERVER_BUILDER
                .comment("Added Spell Resist per enchantment level.")
                .defineInRange("spell_resist_per_level", 2f, 0f, 99f);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("gun schematic trade prices");
            for (var type : GunsRegistry.GunTypes.values())
            {
                GUNTYPE_SCHEMATIC_PRICES.put(type, SERVER_BUILDER
                    .comment(String.format("The price in Emeralds for %s gun schematics", type))
                    .defineInRange(String.format("%s_prices", type.toString().toLowerCase()), DEFAULT_GUNTYPE_SCHEMATIC_PRICES.get(type), 1, 999));
            }
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("auxi settings");
            AUXI_WEAPON_DURABILITY = SERVER_BUILDER
                .defineInRange("auxiliary_weapons_durability", 3150, 1, Integer.MAX_VALUE);
            AUXI_SPEED = SERVER_BUILDER
                .defineInRange("fulcaligr_attack_damage", 2d, 1, Integer.MAX_VALUE);
            FULCALIGR_ATTACK_DAMAGE = SERVER_BUILDER
                .defineInRange("fulcaligr_attack_damage", 46, 1, Integer.MAX_VALUE);
            FULCALIGR_ATTACK_SPEED = SERVER_BUILDER
                .defineInRange("fulcaligr_attack_speed", -2.5d, 1, Integer.MAX_VALUE);
        SERVER_BUILDER.pop();
        SERVER_CONFIG = SERVER_BUILDER.build();
        //endregion

        //region Common
        COMMON_CONFIG = SERVER_CONFIG;
        //endregion

        //region Client
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
        //endregion
    }

    public static void register()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

    public static boolean isItemExists(final Object obj)
    {
        return obj instanceof final String itemName
            && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    public static int getGunPrice(GunsRegistry.GunTypes type)
    {
        return GUNTYPE_SCHEMATIC_PRICES.get(type).get();
    }
}
