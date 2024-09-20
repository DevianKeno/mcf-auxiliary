package net.hm1.auxiliary.event;

import java.util.List;

import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkProvider;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.armor.MagicArmor;
import net.hm1.auxiliary.capability.IMagicArmorCapability;
import net.hm1.auxiliary.capability.MagicArmorCapabilityProvider;
import net.hm1.auxiliary.items.ModItems;
import net.hm1.auxiliary.villager.ModVillagers;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class ModEvents
{
    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<ItemStack> event)
    {
        setupMagicArmorCapabilities(event);
    }

    static void setupMagicArmorCapabilities(AttachCapabilitiesEvent<ItemStack> event)
    {
//        if (event.getObject().getCapability(MagicArmorCapabilityProvider.MAGIC_ARMOR_CAPABILITY).isPresent()) return;

        if (event.getObject().getItem() instanceof ArmorItem armorItem) {
            if (ForgeRegistries.ITEMS.getKey(armorItem).equals(new ResourceLocation("irons_spellbooks", "wandering_magician_helmet"))) {
                ICapabilityProvider provider = new ICapabilityProvider()
                {
                    MagicArmor magicArmor;
                    LazyOptional<IMagicArmorCapability> magicArmorCapability = LazyOptional.of(() -> magicArmor);
                    Capability<IMagicArmorCapability> capability = CapabilityManager.get(new CapabilityToken<IMagicArmorCapability>(){});

                    @Override
                    public <T> @NotNull LazyOptional<T> getCapability(Capability<T> cap, Direction direction) {
                        if (cap == this.capability) {
                            return magicArmorCapability.cast();
                        }
                        return LazyOptional.empty();
                    }
                };

                event.addCapability(new ResourceLocation(Auxiliary.MOD_ID, "magic_armor"), provider);
            }
        }
    }

    @SubscribeEvent
    public void registerCaps(RegisterCapabilitiesEvent event)
    {
        event.register(IMagicArmorCapability.class);
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
