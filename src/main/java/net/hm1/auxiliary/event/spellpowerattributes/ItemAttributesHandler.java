package net.hm1.auxiliary.event.spellpowerattributes;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.hm1.auxiliary.utils.Utils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class ItemAttributesHandler
{
    static final UUID[] SLOT_UUIDS = new UUID[]{
        UUID.fromString("D8499B04-0E66-4A16-9D6D-358E75F15C12"), // HELMET
        UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), // CHESTPLATE
        UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"), // LEGGINGS
        UUID.fromString("C49E5746-8766-4929-B2F2-48CCB15ED956")  // BOOTS
    };

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemAttributeModify(ItemAttributeModifierEvent event)
    {
        if (!(event.getItemStack().getItem() instanceof ArmorItem armor)) return;
        if (event.getSlotType() != armor.getEquipmentSlot()) return;

        Enchantment ench;
        ench = Utils.getEnchantment("spell_power:spell_power");
        if (Utils.hasEnchantment(event.getItemStack(), ench))
        {
            event.addModifier(
                io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER.get(),
                new AttributeModifier(
                    getSlotUUID(event.getSlotType()),
                    "spell_power",
                    AuxiliaryConfig.Enchantments.SPELL_POWER_PER_LEVEL.get() * event.getItemStack().getEnchantmentLevel(ench),
                    AttributeModifier.Operation.ADDITION));
        }
        ench = Utils.getEnchantment("spell_power:haste");
        if (Utils.hasEnchantment(event.getItemStack(), ench))
        {
            event.addModifier(
                io.redspace.ironsspellbooks.api.registry.AttributeRegistry.CAST_TIME_REDUCTION.get(),
                new AttributeModifier(
                    getSlotUUID(event.getSlotType()),
                    "cast_time_reduction",
                    AuxiliaryConfig.Enchantments.SPELL_HASTE_PER_LEVEL.get() * event.getItemStack().getEnchantmentLevel(ench),
                    AttributeModifier.Operation.ADDITION));
        }
        ench = Utils.getEnchantment("spell_power:magic_protection");
        if (Utils.hasEnchantment(event.getItemStack(), ench))
        {
            event.addModifier(
                io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_RESIST.get(),
                new AttributeModifier(
                    getSlotUUID(event.getSlotType()),
                    "spell_resist",
                    AuxiliaryConfig.Enchantments.SPELL_RESIST_PER_LEVEL.get() * event.getItemStack().getEnchantmentLevel(ench),
                    AttributeModifier.Operation.ADDITION));
        }
    }

    static UUID getSlotUUID(EquipmentSlot slot)
    {
        return switch (slot)
        {
            case HEAD -> SLOT_UUIDS[0];
            case CHEST -> SLOT_UUIDS[1];
            case LEGS -> SLOT_UUIDS[2];
            case FEET -> SLOT_UUIDS[3];
            default -> UUID.randomUUID();
        };
    }
}
