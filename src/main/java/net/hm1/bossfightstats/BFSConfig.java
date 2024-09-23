package net.hm1.bossfightstats;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BFSConfig
{
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.ConfigValue<List<String>> TRACKED_ENTITIES;
    public static List<String> DEFAULT_TRACKED_ENTITIES = new ArrayList<>(List.of(
        "minecraft:ender_dragon",
        "minecraft:wither",
        "minecraft:warden"));
    public static ForgeConfigSpec.BooleanValue COUNT_ALL_DEATHS;
    public static ForgeConfigSpec.BooleanValue RESET_ON_DEFEAT;

    public static class Track
    {
        public static ForgeConfigSpec.BooleanValue DAMAGE_DEALT;
        public static ForgeConfigSpec.BooleanValue DAMAGE_TAKEN;
        public static ForgeConfigSpec.BooleanValue DEATHS;
    }

    static
    {
        var commonBuilder = new ForgeConfigSpec.Builder();

        commonBuilder.push("tracked bosses");

            commonBuilder.comment("List of entity ids to track damage stats to");
            TRACKED_ENTITIES = commonBuilder
                .define("tracked_entities", DEFAULT_TRACKED_ENTITIES);
            Track.DAMAGE_DEALT = commonBuilder
                .comment("Whether to track total damage dealt to the boss during fight")
                .define("track_damage_dealt", true);
            Track.DAMAGE_TAKEN = commonBuilder
                .comment("Whether to track total damage taken during boss fight")
                .define("track_damage_taken", true);
            Track.DEATHS = commonBuilder
                .comment("Whether to track total death count during boss fight")
                .define("track_deaths", true);
            COUNT_ALL_DEATHS = commonBuilder
                .comment("Whether to count deaths caused by all sources.")
                .comment("If false, only counts deaths cause by tracked entities during the fight.")
                .define("count_all_deaths", true);
            RESET_ON_DEFEAT = commonBuilder
                .comment("Whether to reset stats if all players had died")
                .define("reset_on_defeat", true);

        commonBuilder.pop();

        COMMON_CONFIG = commonBuilder.build();
    }

    public static boolean isTracked(Entity entity)
    {
        var type = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return type != null && TRACKED_ENTITIES.get().contains(type.toString());
    }


    public static void register()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }

}
