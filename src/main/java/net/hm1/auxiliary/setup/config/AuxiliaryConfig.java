package net.hm1.auxiliary.setup.config;

import net.hm1.auxiliary.Auxiliary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AuxiliaryConfig
{
    public static void register()
    {
        registerServer();
        //registerCommon();
        //registerClient();
    }

    public static class MagicArmorConfig
    {
        public static ForgeConfigSpec.IntValue WANDERING_MAGICIAN_THREADS_MAX_TIER;
        public static ForgeConfigSpec.IntValue PUMPKIN_THREADS_MAX_TIER;
        public static ForgeConfigSpec.IntValue NETHERITE_MAGE_THREADS_MAX_TIER;
    }

    public static ForgeConfigSpec.IntValue AUXI_WEAPON_DURABILITY;
    public static ForgeConfigSpec.DoubleValue AUXI_SPEED;
    public static ForgeConfigSpec.IntValue FULCALIGR_ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue FULCALIGR_ATTACK_SPEED;

    public static void registerServer()
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("server config");

        builder.push("magic armor settings");
        MagicArmorConfig.WANDERING_MAGICIAN_THREADS_MAX_TIER = builder
            .defineInRange("wandering_magician_threads_max_tiers", 3, 0, 8);
        MagicArmorConfig.PUMPKIN_THREADS_MAX_TIER = builder
            .defineInRange("scarecrow_threads_max_tiers", 4, 0, 8);
        MagicArmorConfig.NETHERITE_MAGE_THREADS_MAX_TIER = builder
            .defineInRange("netherite_battlemage_max_tiers", 5, 0, 8);
        builder.pop();

        builder.push("auxi settings");
        AUXI_WEAPON_DURABILITY = builder
            .defineInRange("auxiliary_weapons_durability", 3150, 1, Integer.MAX_VALUE);
        AUXI_SPEED = builder
            .defineInRange("fulcaligr_attack_damage", 2d, 1, Integer.MAX_VALUE);
        FULCALIGR_ATTACK_DAMAGE = builder
            .defineInRange("fulcaligr_attack_damage", 46, 1, Integer.MAX_VALUE);
        FULCALIGR_ATTACK_SPEED = builder
            .defineInRange("fulcaligr_attack_speed", -2.5d, 1, Integer.MAX_VALUE);
        builder.pop();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, builder.build());
    }
}
