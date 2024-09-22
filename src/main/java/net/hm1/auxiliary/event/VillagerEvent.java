package net.hm1.auxiliary.event;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.init.ModItems;
import net.hm1.auxiliary.init.ModVillagers;
import net.hm1.auxiliary.items.GunSchematic;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.hm1.auxiliary.utils.Itemf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.hm1.auxiliary.setup.registry.GunsRegistry.GunTypes.*;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class VillagerEvent
{
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event)
    {
        if (event.getType() == ModVillagers.GUNSMITH.get())
        {
            addGunsmithTrades(event);
        }
    }

    static void addGunsmithTrades(VillagerTradesEvent event)
    {
        var trades = event.getTrades();

        final MerchantOffer[] NOVICE_TRADES =
        {
            new MerchantOffer(
                item(Items.GUNPOWDER, 3),
                item(Items.EMERALD, 1),
                16, 2, 0.05f),

            new MerchantOffer(
                item(Items.COAL, 16),
                item(Items.EMERALD, 1),
                16, 2, 0.05f),

            new MerchantOffer(
                item(Items.EMERALD, AuxiliaryConfig.getGunPrice(HANDGUN)),
                item(GunSchematic.ofRandom(HANDGUN).getItem(), 1),
                1, 4, 0.02f),
        };

        final MerchantOffer[] APPRENTICE_TRADES =
        {
            new MerchantOffer(
                item(Items.EMERALD, 1),
                item(Items.GUNPOWDER, 2),
                12, 5, 0.02f),

            new MerchantOffer(
                item(Items.EMERALD, 1),
                item(Items.COAL, 1),
                12, 5, 0.02f),
        };

        final MerchantOffer[] JOURNEYMAN_TRADES =
        {
            new MerchantOffer(
                item(Items.EMERALD, 1),
                item(Items.STONE, 4), /// THIS SHOULD BE LIMESTONE T-T
                12, 8, 0.05f),

            new MerchantOffer(
                item(Items.EMERALD, AuxiliaryConfig.getGunPrice(ASSAULT_RIFLE)),
                item(GunSchematic.ofRandom(ASSAULT_RIFLE).getItem(), 1),
                1, 12, 0.02f),
        };

        final MerchantOffer[] EXPERT_TRADES =
        {
            new MerchantOffer(
                item(ModItems.GUN_SCHEMATIC.get(), 1),
                item(Items.GUNPOWDER, 2),
                16, 10, 0.05f),

            new MerchantOffer(
                item(Items.EMERALD, AuxiliaryConfig.getGunPrice(SNIPER_RIFLE)),
                item(GunSchematic.ofRandom(SNIPER_RIFLE).getItem(), 1),
                1, 5, 0.02f),
        };

        final MerchantOffer[] MASTER_TRADES =
        {
            new MerchantOffer(
                item(Items.EMERALD, 1),
                item(Items.GUNPOWDER, 2),
                12, 8, 0.02f),

            new MerchantOffer(
                item(Items.EMERALD, 1),
                item(Items.GUNPOWDER, 2),
                12, 8, 0.02f),

            new MerchantOffer(
                item(Items.EMERALD, 50),
                item(Itemf.item("create:precision_mechanism"), 1),
                4, 10, 0.02f),

            new MerchantOffer(
                item(Items.EMERALD, AuxiliaryConfig.getGunPrice(MACHINE_GUN)),
                item(GunSchematic.ofRandom(MACHINE_GUN).getItem(), 1),
                1, 5, 0.02f),
        };

        /// NOVICE
        trades.get(1).add((pTrader, pRandom) -> {
            var selectedTrades = randomize(pRandom, NOVICE_TRADES, 2);
            return selectedTrades.get(pRandom.nextInt(selectedTrades.size()));
        });

        /// APPRENTICE
        trades.get(2).add((pTrader, pRandom) ->
        {
            var selectedTrades = randomize(pRandom, APPRENTICE_TRADES, 2);
            if (pRandom.nextBoolean()) {
                selectedTrades.add(new MerchantOffer(
                    item(Items.EMERALD, AuxiliaryConfig.GUNTYPE_SCHEMATIC_PRICES.get(SHOTGUN).get()),
                    item(GunSchematic.ofRandom(SHOTGUN).getItem(), 1),
                    1, 8, 0.02f));
            } else {
                selectedTrades.add(new MerchantOffer(
                    item(Items.EMERALD, AuxiliaryConfig.GUNTYPE_SCHEMATIC_PRICES.get(SUBMACHINE_GUN).get()),
                    item(GunSchematic.ofRandom(SUBMACHINE_GUN).getItem(), 1),
                    1, 8, 0.02f));
            }
            return selectedTrades.get(pRandom.nextInt(selectedTrades.size()));
        });

        /// JOURNEYMAN
        trades.get(3).add((pTrader, pRandom) -> {
            var selectedTrades = randomize(pRandom, JOURNEYMAN_TRADES, 2);
            return selectedTrades.get(pRandom.nextInt(selectedTrades.size()));
        });

        /// EXPERT
        trades.get(4).add((pTrader, pRandom) -> {
            var selectedTrades = randomize(pRandom, EXPERT_TRADES, 2);
            return selectedTrades.get(pRandom.nextInt(selectedTrades.size()));
        });

        /// MASTER
        trades.get(5).add((pTrader, pRandom) -> {
            var selectedTrades = randomize(pRandom, MASTER_TRADES, 2);
            return selectedTrades.get(pRandom.nextInt(selectedTrades.size()));
        });
    }

    public static ItemStack item(Item item, int count)
    {
        return new ItemStack(item, count);
    }

    private static List<MerchantOffer> randomize(RandomSource random, MerchantOffer[] offers, int count)
    {
        var offerList = new ArrayList<>(List.of(offers));
        Collections.shuffle(offerList);
        return offerList.subList(0, Math.min(count, offerList.size()));
    }
}
