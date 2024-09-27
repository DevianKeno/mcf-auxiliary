package net.hm1.auxiliary.enchantment;

import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BallisticsEnchantment extends Enchantment
{
    public BallisticsEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[]{ EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET });
    }

    public int getMinLevel() { return 1; }
    public int getMaxLevel() { return AuxiliaryConfig.Enchantments.BALLISTICS_MAX_LEVEL.get(); }
    public int getMinCost(int pLevel) { return (pLevel * 9) + 1; }
    public int getMaxCost(int pLevel) { return this.getMinCost(pLevel) + 5; }
}
