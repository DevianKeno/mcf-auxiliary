package net.hm1.auxiliary.init;

import java.util.List;

import com.google.common.collect.ImmutableMultimap;
import com.hollingsworth.arsnouveau.api.perk.*;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.init.ModItems;
import net.hm1.auxiliary.init.ModTags;
import net.hm1.auxiliary.init.ModVillagers;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.ItemAttributeModifierEvent;
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
}
