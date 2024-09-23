package net.hm1.bossfightstats.event;

import net.hm1.bossfightstats.BossFightStats;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BossFightStats.MOD_ID)
public class LivingDeathEventHandler
{
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        if (!BossFightManager.isOnFight()) return;
        if (event.getEntity().level().isClientSide) return;

        if ((event.getEntity() instanceof Player player))
        {
            BossFightManager.addPlayerDeathCaused(player, event.getSource().getDirectEntity());
        }
        else if (BossFightManager.isTracked(event.getEntity()))
        {
            BossFightManager.finishBoss(event.getEntity());
        }
    }
}
