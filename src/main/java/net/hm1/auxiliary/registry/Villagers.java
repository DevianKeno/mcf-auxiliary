package net.hm1.auxiliary.registry;

import com.google.common.collect.ImmutableSet;
import net.hm1.auxiliary.Auxiliary;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Villagers
{
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Auxiliary.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Auxiliary.MOD_ID);

    public static final RegistryObject<PoiType> GUNSMITH_POI;
    public static final RegistryObject<VillagerProfession> GUNSMITH;

    static
    {
        GUNSMITH_POI = POI_TYPES.register("gunsmith_poi", () ->
            new PoiType(ImmutableSet.copyOf(com.tacz.guns.init.ModBlocks.GUN_SMITH_TABLE.get().getStateDefinition().getPossibleStates()), 1, 1));

        GUNSMITH = VILLAGER_PROFESSIONS.register("gunsmith", () ->
            new VillagerProfession("gunsmith",
                holder -> holder.get() == GUNSMITH_POI.get(),
                holder -> holder.get() == GUNSMITH_POI.get(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                SoundEvents.VILLAGER_WORK_ARMORER));
    }

    public static void register(IEventBus eventBus)
    {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
