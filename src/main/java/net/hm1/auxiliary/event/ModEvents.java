package net.hm1.auxiliary.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableMultimap;
import com.hollingsworth.arsnouveau.api.perk.*;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.items.ModItems;
import net.hm1.auxiliary.items.ModTags;
import net.hm1.auxiliary.villager.ModVillagers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class ModEvents
{
    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event)
    {
        var itemStack = event.getItemStack();

        if (!itemStack.is(ModTags.MAGIC_ARMOR)) return;
        if (!(itemStack.getItem() instanceof ArmorItem armor)) return;
        if (event.getSlotType() != armor.getEquipmentSlot()) return;
        if (!(PerkUtil.getPerkHolder(itemStack) instanceof ArmorPerkHolder armorPerkHolder)) return;

        var attributes = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();

        for (var p : armorPerkHolder.getPerkInstances()){
            attributes.putAll(p.getPerk().getModifiers(armor.getEquipmentSlot(), itemStack, p.getSlot().value));
        }
        for (var entry : attributes.build().entries()) {
            event.addModifier(entry.getKey(), entry.getValue());
        }
    }

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
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        final MerchantOffer[] noviceTrades =
        {
            new MerchantOffer(
                new ItemStack(Items.GUNPOWDER, 3),
                new ItemStack(Items.EMERALD, 1),
                16, 2, 0.05f),

            new MerchantOffer(
                new ItemStack(Items.COAL, 16),
                new ItemStack(Items.EMERALD, 1),
                16, 2, 0.05f),
        };

        final MerchantOffer[] apprenticeTrades =
        {
            new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.GUNPOWDER, 2),
                12, 5, 0.02f),

            new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.COAL, 1),
                12, 5, 0.02f),
        };

        final MerchantOffer[] journeymanTrades =
        {
            new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.STONE, 4),
                12, 8, 0.05f),
        };

        final MerchantOffer[] expertTrades =
        {
            new MerchantOffer(
                new ItemStack(ModItems.UNRESEARCHED_GUN_SCHEMATIC.get(), 1),
                new ItemStack(Items.GUNPOWDER, 2),
                16, 10, 0.05f),
        };

        final MerchantOffer[] masterTrades =
        {
            new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.GUNPOWDER, 2),
                12, 8, 0.02f),

            new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(Items.GUNPOWDER, 2),
                12, 8, 0.02f),
        };

        /// Add trades
        for (var offer : noviceTrades)
        {
            trades.get(1).add((pTrader, pRandom) -> offer);
        }

        for (var offer : apprenticeTrades)
        {
            trades.get(2).add((pTrader, pRandom) -> offer);
        }

        for (var offer : journeymanTrades)
        {
            trades.get(3).add((pTrader, pRandom) -> offer);
        }

        for (var offer : expertTrades)
        {
            trades.get(4).add((pTrader, pRandom) -> offer);
        }

        for (var offer : masterTrades)
        {
            trades.get(5).add((pTrader, pRandom) -> offer);
        }
    }
}
