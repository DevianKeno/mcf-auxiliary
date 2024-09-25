package net.hm1.auxiliary.event.spellpowerattributes;

import com.google.common.collect.ImmutableMultimap;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import com.hollingsworth.arsnouveau.setup.registry.EnchantmentRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.CooldownInstance;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.init.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class ItemAttributeModifierEventHandler
{
    /*
     * Apply Spell Power Attribute enchantments
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemTooltip(ItemAttributeModifierEvent event)
    {
        if (!(event.getItemStack().getItem() instanceof ArmorItem armor)) return;
        if (event.getSlotType() != armor.getEquipmentSlot()) return;

        var attributes = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
        UUID uuid;
//        try
//        {
//            if (hasEnchantment(event.getItemStack(), getEnchantment("spell_power:spell_power")))
//            {
//                event.addModifier(
//                    io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER.get(),
//                    new AttributeModifier(
//                        getEnchantBoostBySlot(event.getSlotType(),
//                            "spell_haste",
//                            1D,
//                            AttributeModifier.Operation.ADDITION)));
//            }
//            if (hasEnchantment(event.getItemStack(), getEnchantment("spell_power:spell_haste")))
//            {
//                event.addModifier(
//                    io.redspace.ironsspellbooks.api.registry.AttributeRegistry.CAST_TIME_REDUCTION.get(),
//                    new AttributeModifier(
//                        getEnchantBoostBySlot(event.getSlotType(),
//                        "spell_haste",
//                        1D,
//                        AttributeModifier.Operation.ADDITION)));
//            }
//            if (hasEnchantment(event.getItemStack(), getEnchantment("spell_power:magic_protection")))
//            {
////                event.addModifier(
////                    io.redspace.ironsspellbooks.api.registry.AttributeRegistry..get(),
////                    new AttributeModifier(
////                        getEnchantBoostBySlot(event.getSlotType(),
////                            "spell_haste",
////                            1D,
////                            AttributeModifier.Operation.ADDITION)));
//            }
//        }
//        catch (Exception e)
//        {
//            Auxiliary.LOGGER.warn("Enchantment error");
//        }
    }

    @Nullable
    static Enchantment getEnchantment(String id)
    {
        return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
    }

    static boolean hasEnchantment(ItemStack stack, @Nullable Enchantment enchantment)
    {
        return enchantment != null && stack.getEnchantmentLevel(enchantment) > 0;
    }

}
