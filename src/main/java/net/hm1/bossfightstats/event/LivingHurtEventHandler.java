package net.hm1.bossfightstats.event;

import net.hm1.bossfightstats.BossFightStats;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BossFightStats.MOD_ID)
public class LivingHurtEventHandler
{
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        if (!BossFightManager.isOnFight()) return;
        if (event.getEntity().level().isClientSide()) return;

        if (isPlayerHurtByTrackedEntity(event))
        {
            BossFightManager.addPlayerDamageTaken((Player) event.getEntity(), event.getSource().getEntity(), event.getAmount());
        }
        else if (isTrackedEntityHurtByPlayer(event))
        {
            BossFightManager.addPlayerDamageDealt((Player) event.getSource().getEntity(), event.getEntity(), event.getAmount());
        }
    }

    static boolean isPlayerHurtByTrackedEntity(LivingHurtEvent event)
    {
        return (event.getEntity() instanceof Player) && BossFightManager.isTracked(event.getSource().getEntity());
    }

    static boolean isTrackedEntityHurtByPlayer(LivingHurtEvent event)
    {
        return BossFightManager.isTracked(event.getEntity()) && (event.getSource().getEntity() instanceof Player);
    }
}
