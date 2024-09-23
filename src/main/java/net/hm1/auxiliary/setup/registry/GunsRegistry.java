package net.hm1.auxiliary.setup.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.stream.Stream;

public class GunsRegistry
{
    public enum GunTypes { HANDGUN, SUBMACHINE_GUN, SHOTGUN, ASSAULT_RIFLE, SNIPER_RIFLE, DESIGNATED_MARKSMAN_RIFLE, MACHINE_GUN, SPECIAL }
    public enum Source { TACZ, POINTBLANK }
    public static final String UNKNOWN_GUN = "Unknown Gun";

    public static class TACZ
    {
        public static final String[] ALL_HANDGUN_IDS = { "cz75", "deagle", "deagle_golden", "glock_17" };
        public static final String[] ALL_SMG_IDS = { "hk_mp5a5", "ump45", "uzi", "vector45" };
        public static final String[] ALL_SG_IDS = { "aa12", "db_long", "db_short" };
        public static final String[] ALL_AR_IDS = { "ak47", "m4a1", "m16a1", "m16a4", "scar_h" };
        public static final String[] ALL_SR_IDS = { "ai_awp", "m95" };
        public static final String[] ALL_DMR_IDS = { "hk_g3", "sks_tactical" };
        public static final String[] ALL_MG_IDS = { "m249", "rpk" };
        public static final String[] ALL_SPECIAL_IDS = { "rpg7" };

        public static String[] getIdsOf(GunTypes type)
        {
            return switch (type)
            {
                case HANDGUN -> ALL_HANDGUN_IDS;
                case SUBMACHINE_GUN -> ALL_SMG_IDS;
                case SHOTGUN -> ALL_SG_IDS;
                case ASSAULT_RIFLE -> ALL_AR_IDS;
                case SNIPER_RIFLE -> ALL_SR_IDS;
                case DESIGNATED_MARKSMAN_RIFLE -> ALL_DMR_IDS;
                case MACHINE_GUN -> ALL_MG_IDS;
                case SPECIAL -> ALL_SPECIAL_IDS;
            };
        }
    }

    public static class Pointblank
    {
        public static final String[] ALL_HANDGUN_IDS = { "m1911a1", "tti_viper", "p30l", "mk23", "glock17", "glock18", "m9", "deserteagle", "rhino" };
        public static final String[] ALL_SMG_IDS = { "mp5", "mp7", "ump45", "vector", "p90", "tmp", "m950" };
        public static final String[] ALL_SG_IDS = { "m590", "m870", "spas12", "m1014", "aa12", "citorixs", "hs12" };
        public static final String[] ALL_AR_IDS = { "m4a1", "m4a1mod1", "ar57", "ro635", "star15", "m4sopmodii", "m16a1", "hk416", "scarl", "xm7", "xm29", "g36c", "g36k", "ak47", "ak74", "ak12", "an94", "aug", "g41" };
        public static final String[] ALL_SR_IDS = { "xm3", "c14", "l96a1", "ballista", "gm6lynx" };
        public static final String[] ALL_DMR_IDS = { "sl8", "mk14ebr", "uar10", "g3", "wa2000" };
        public static final String[] ALL_MG_IDS = { "aughbar", "lamg", "mk48", "m249" };
        public static final String[] ALL_SPECIAL_IDS = { "m32mgl", "smaw", "javelin", "at4", "m134minigun" };

        public static String[] getIdsOf(GunTypes type)
        {
            return switch (type)
            {
                case HANDGUN -> ALL_HANDGUN_IDS;
                case SUBMACHINE_GUN -> ALL_SMG_IDS;
                case SHOTGUN -> ALL_SG_IDS;
                case ASSAULT_RIFLE -> ALL_AR_IDS;
                case SNIPER_RIFLE -> ALL_SR_IDS;
                case DESIGNATED_MARKSMAN_RIFLE -> ALL_DMR_IDS;
                case MACHINE_GUN -> ALL_MG_IDS;
                case SPECIAL -> ALL_SPECIAL_IDS;
            };
        }
    }

    static Random random = new Random();

    public static String getRandomGunIdOf(GunTypes gunType)
    {
        return switch (gunType)
        {
            case HANDGUN -> getRandomHandgunId();
            case SUBMACHINE_GUN -> getRandomSMGId();
            case SHOTGUN -> getRandomSGId();
            case ASSAULT_RIFLE -> getRandomARId();
            case SNIPER_RIFLE -> getRandomSRId();
            case DESIGNATED_MARKSMAN_RIFLE -> getRandomDMRId();
            case MACHINE_GUN -> getRandomMGId();
            case SPECIAL -> getRandomSpecialId();
        };
    }

    public static ItemStack getGunItem(String gunId, Source source)
    {
        if (source == Source.TACZ)
        {
            var gun = com.tacz.guns.init.ModItems.MODERN_KINETIC_GUN.get();
            var gunStack = new ItemStack(gun.asItem(), 1);
            gun.setGunId(gunStack, new ResourceLocation("tacz", gunId));
            return gunStack;
        }
        else if (source == Source.POINTBLANK)
        {
            var gun = ForgeRegistries.ITEMS.getValue(new ResourceLocation("pointblank", gunId));
            if (gun != null && gun != Items.AIR) {
                return new ItemStack(gun.asItem(), 1);
            }
        }

        return new ItemStack(Items.AIR);
    }

    public static String getRandomHandgunId()
    {
        var allHandguns = Stream.concat(Stream.of(TACZ.ALL_HANDGUN_IDS), Stream.of(Pointblank.ALL_HANDGUN_IDS)).toArray(String[]::new);
        return allHandguns[random.nextInt(allHandguns.length)];
    }

    public static String getRandomSMGId()
    {
        var allSMGs = Stream.concat(Stream.of(TACZ.ALL_SMG_IDS), Stream.of(Pointblank.ALL_SMG_IDS)).toArray(String[]::new);
        return allSMGs[random.nextInt(allSMGs.length)];
    }

    public static String getRandomSGId()
    {
        var allSGs = Stream.concat(Stream.of(TACZ.ALL_SG_IDS), Stream.of(Pointblank.ALL_SG_IDS)).toArray(String[]::new);
        return allSGs[random.nextInt(allSGs.length)];
    }

    public static String getRandomARId()
    {
        var allARs = Stream.concat(Stream.of(TACZ.ALL_AR_IDS), Stream.of(Pointblank.ALL_AR_IDS)).toArray(String[]::new);
        return allARs[random.nextInt(allARs.length)];
    }

    public static String getRandomSRId()
    {
        var allSRs = Stream.concat(Stream.of(TACZ.ALL_SR_IDS), Stream.of(Pointblank.ALL_SR_IDS)).toArray(String[]::new);
        return allSRs[random.nextInt(allSRs.length)];
    }

    public static String getRandomDMRId()
    {
        var allDMRs = Stream.concat(Stream.of(TACZ.ALL_DMR_IDS), Stream.of(Pointblank.ALL_DMR_IDS)).toArray(String[]::new);
        return allDMRs[random.nextInt(allDMRs.length)];
    }

    public static String getRandomMGId()
    {
        var allMGs = Stream.concat(Stream.of(TACZ.ALL_MG_IDS), Stream.of(Pointblank.ALL_MG_IDS)).toArray(String[]::new);
        return allMGs[random.nextInt(allMGs.length)];
    }

    public static String getRandomSpecialId()
    {
        var allSpecials = Stream.concat(Stream.of(TACZ.ALL_SPECIAL_IDS), Stream.of(Pointblank.ALL_SPECIAL_IDS)).toArray(String[]::new);
        return allSpecials[random.nextInt(allSpecials.length)];
    }
}
