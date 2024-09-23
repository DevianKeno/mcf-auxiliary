package net.hm1.bossfightstats.event;

import net.hm1.bossfightstats.BFSConfig;
import net.hm1.bossfightstats.BossFightStats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BossFightStats.MOD_ID)
public class EntityJoinLevelHandler
{
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event)
    {
        if (event.getLevel().isClientSide) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!BFSConfig.isTracked(event.getEntity())) return;

        BossFightStats.LOGGER.info("[BossFightStats] Begun tracking stats for entity: " + event.getEntity().getName().getString());
        BossFightManager.startTrack(event.getEntity());
    }
}
