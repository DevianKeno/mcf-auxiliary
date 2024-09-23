package net.hm1.bossfightstats.event;

import net.hm1.bossfightstats.BFSConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class BossFightManager
{
    public static class TrackedStats
    {
        public TrackedStats(Entity entity)
        {
            this.entity = entity;
        }

        Entity entity;
        public Entity getEntity() { return entity; }
        public double DamageDealt = 0f;
        public double DamageTaken = 0f;
        public int Deaths = 0;
    }

    /// Key is EntityUUID, Value is (Key is PlayerUUID, Value is TrackedStats)
    static final Map<UUID, Map<UUID, TrackedStats>> trackedEntities = new HashMap<>();
    static final Map<UUID, Player> trackedPlayers = new HashMap<>();
    static boolean onFight;
    public static boolean isOnFight() { return onFight; }

    public static void startTrack(Entity etty)
    {
        trackedEntities.putIfAbsent(etty.getUUID(), new HashMap<>());
        onFight = true;
    }


    public static void endTrack(LivingEntity livingEtty)
    {
        trackedEntities.remove(livingEtty.getUUID());

        if (trackedEntities.isEmpty()) onFight = false;
    }

    public static void updateTracker(Entity etty)
    {
        /// TODO: remove dead entity UUIDs that is still present in map
    }

    public static void addPlayerDamageDealt(Player player, Entity entity, float amount)
    {
        if (player == null || entity == null) return;

        var statsMap = trackedEntities.get(entity.getUUID());
        if (statsMap != null)
        {
            trackedPlayers.putIfAbsent(player.getUUID(), player);
            statsMap.putIfAbsent(player.getUUID(), new TrackedStats(player));
            statsMap.get(player.getUUID()).DamageDealt += amount;
        }
    }

    public static void addPlayerDamageTaken(Player player, Entity source, float amount)
    {
        if (player == null || source == null) return;

        var statsMap = trackedEntities.get(source.getUUID());
        if (statsMap != null)
        {
            trackedPlayers.putIfAbsent(player.getUUID(), player);
            statsMap.putIfAbsent(player.getUUID(), new TrackedStats(player));
            statsMap.get(player.getUUID()).DamageTaken += amount;
        }
    }

    public static void addPlayerDeathCaused(Player player, Entity killedBy)
    {
        if (!BFSConfig.COUNT_ALL_DEATHS.get() && !isTracked(killedBy)) return;

        var statsMap = trackedEntities.get(killedBy.getUUID());
        if (statsMap != null)
        {
            trackedPlayers.putIfAbsent(player.getUUID(), player);
            statsMap.putIfAbsent(player.getUUID(), new TrackedStats(player));
            statsMap.get(player.getUUID()).Deaths++;

            if (BFSConfig.RESET_ON_DEFEAT.get())
            {
                boolean reset = false;
                for (var p : trackedPlayers.values()) {
                    if (p.isAlive()) {
                        reset = false;
                        break;
                    }
                    reset = true;
                }
                if (reset) resetTrackedStatsAll();
            }
        }
    }

    public static void resetTrackedStatsAll()
    {
        for (var statMap : trackedEntities.entrySet())
        {
            for (var trackedStat : statMap.getValue().entrySet())
            {
                trackedStat.setValue(new TrackedStats(trackedStat.getValue().getEntity()));
            }
        }
    }

    public static Map<UUID, TrackedStats> getPlayerTrackedStatsFrom(Entity entity)
    {
        var statsMap = trackedEntities.get(entity.getUUID());
        if (statsMap != null) return statsMap;
        return new HashMap<>();
    }

    public static boolean isTracked(Entity entity)
    {
        return entity != null && trackedEntities.containsKey(entity.getUUID());
    }

    public static void finishBoss(Entity entity)
    {
        if (!onFight) return;

        var playersToMessage = new ArrayList<Player>();
        var lines = new ArrayList<Component>();
        boolean sendMessage = false;

        lines.add(Component.translatable("bossfightstats.title").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
            .append(Component.literal(entity.getDisplayName().getString()).withStyle(ChatFormatting.RED)));

        for (var entry : getPlayerTrackedStatsFrom(entity).entrySet())
        {
            sendMessage = true;
            var player = entity.level().getPlayerByUUID(entry.getKey());
            if (player == null) continue;

            playersToMessage.add(player);
            lines.add(Component.literal("[").withStyle(ChatFormatting.WHITE)
                .append(Component.literal(player.getDisplayName().getString()).withStyle(ChatFormatting.GREEN))
                .append(Component.literal("]").withStyle(ChatFormatting.WHITE)));

            if (BFSConfig.Track.DAMAGE_DEALT.get())
            {
                lines.add(Component.translatable("bossfightstats.damage_dealt")
                    .withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(toStringFloored(entry.getValue().DamageDealt)).withStyle(ChatFormatting.GREEN)));
            }
            if (BFSConfig.Track.DAMAGE_TAKEN.get())
            {
                lines.add(Component.translatable("bossfightstats.damage_taken").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(toStringFloored(entry.getValue().DamageTaken)).withStyle(ChatFormatting.GREEN)));
            }
            if (BFSConfig.Track.DEATHS.get())
            {
                lines.add(Component.translatable("bossfightstats.death_count").withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(toStringFloored(entry.getValue().Deaths)).withStyle(ChatFormatting.GREEN)));
            }
        }

        if (sendMessage)
        {
            for (var player : playersToMessage)
                for (var line : lines)
                {
                    if (player == null) break;
                    player.sendSystemMessage(line);
                }
        }

        endTrack((LivingEntity) entity);
    }

    static String toStringFloored(double value)
    {
        return String.valueOf((int) Math.floor(value));
    }
}
