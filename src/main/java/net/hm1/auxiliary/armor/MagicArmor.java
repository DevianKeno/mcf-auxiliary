package net.hm1.auxiliary.armor;

import com.hollingsworth.arsnouveau.api.mana.IManaEquipment;
import com.hollingsworth.arsnouveau.api.perk.*;

import java.util.*;

import net.hm1.auxiliary.capability.IMagicArmorCapability;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class MagicArmor extends ArmorItem implements IManaEquipment, IMagicArmorCapability
{
    public static final List<PerkSlot> PERK_SLOTS_EMPTY = List.of();
    public static final List<PerkSlot> PERK_SLOTS_T1 = List.of(PerkSlot.ONE);
    public static final List<PerkSlot> PERK_SLOTS_T2 = List.of(PerkSlot.ONE, PerkSlot.ONE);
    public static final List<PerkSlot> PERK_SLOTS_T3 = List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.ONE);
    public static final List<PerkSlot> PERK_SLOTS_T4 = List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.TWO);
    public static final List<PerkSlot> PERK_SLOTS_T5 = List.of(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.TWO);
    public static final List<PerkSlot> PERK_SLOTS_T6 = List.of(PerkSlot.TWO, PerkSlot.TWO, PerkSlot.TWO);
    public static final List<PerkSlot> PERK_SLOTS_T7 = List.of(PerkSlot.TWO, PerkSlot.TWO, PerkSlot.THREE);
    public static final List<PerkSlot> PERK_SLOTS_T8 = List.of(PerkSlot.TWO, PerkSlot.THREE, PerkSlot.THREE);
    public static final List<PerkSlot> PERK_SLOTS_T9 = List.of(PerkSlot.THREE, PerkSlot.THREE, PerkSlot.THREE);

    // Key is tier, Value is Perk Slots
    public static final Map<Integer, List<PerkSlot>> PERK_SLOTS_TIER_MAPPINGS = new HashMap<>() {{
        put(0, PERK_SLOTS_EMPTY);
        put(1, PERK_SLOTS_T1);
        put(2, PERK_SLOTS_T2);
        put(3, PERK_SLOTS_T3);
        put(4, PERK_SLOTS_T4);
        put(5, PERK_SLOTS_T5);
        put(6, PERK_SLOTS_T6);
        put(7, PERK_SLOTS_T7);
        put(8, PERK_SLOTS_T8);
        put(9, PERK_SLOTS_T9);
    }};

    public MagicArmor(ArmorMaterial material, ArmorItem.Type slot, Item.Properties builder)
    {
        super(material, slot, builder);
    }

//    public void onInventoryTick(ItemStack stack, Level world, Player player)
//    {
//        if (!world.isClientSide())
//        {
//            RepairingPerk.attemptRepair(stack, player);
//            IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(stack);
//            if (perkHolder == null) return;
//
//            for (var perk : perkHolder.getPerkInstances())
//            {
//                if (!(perk.getPerk() instanceof ITickablePerk tickablePerk)) continue;
//
//                tickablePerk.tick(stack, world, player, perk);
//            }
//        }
//    }

//    @Override
//    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
//    {
//        var attributes = new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
//        attributes.putAll(super.getDefaultAttributeModifiers(slot));
//        if (this.type.getSlot() != slot) return attributes.build();
//
//        var uuid = (UUID) ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
//        IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(stack);
//        if (perkHolder != null)
//        {
//            attributes.put(PerkAttributes.MAX_MANA.get(), new AttributeModifier(uuid, "max_mana_armor", 30 * (perkHolder.getTier() + 1), Operation.ADDITION));
//            attributes.put(PerkAttributes.MANA_REGEN_BONUS.get(), new AttributeModifier(uuid, "mana_regen_armor", perkHolder.getTier() + 1, Operation.ADDITION));
//
//            for (var perk : perkHolder.getPerkInstances())
//            {
//                attributes.putAll(perk.getPerk().getModifiers(this.type.getSlot(), stack, perk.getSlot().value));
//            }
//        }
//
//        return attributes.build();
//    }

    public int getMinTier() {
        return 0;
    }
}

