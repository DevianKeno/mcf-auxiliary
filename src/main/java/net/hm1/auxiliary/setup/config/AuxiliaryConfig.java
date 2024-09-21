package net.hm1.auxiliary.setup.config;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.armor.MagicArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AuxiliaryConfig
{
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static class MagicArmorConfig
    {
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> MAGIC_ARMOR_ITEMS;
        public static ForgeConfigSpec.BooleanValue MAGIC_ARMOR_IS_THREADABLE;
    }

    public static ForgeConfigSpec.IntValue AUXI_WEAPON_DURABILITY;
    public static ForgeConfigSpec.DoubleValue AUXI_SPEED;
    public static ForgeConfigSpec.IntValue FULCALIGR_ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue FULCALIGR_ATTACK_SPEED;

    public static void register()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

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

        COMMON_CONFIG = SERVER_CONFIG;

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static boolean isItemExists(final Object obj)
    {
        return obj instanceof final String itemName
            && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }
}
