package net.hm1.auxiliary.capability;

import net.hm1.auxiliary.armor.MagicArmor;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicArmorCapabilityProvider implements ICapabilityProvider
{
    public static Capability<IItemHandler> MAGIC_ARMOR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private IMagicArmorCapability magicArmor = null;
    private LazyOptional<IMagicArmorCapability> optional = LazyOptional.of(() -> magicArmor);

    public MagicArmorCapabilityProvider(ArmorItem armorItem)
    {
        magicArmor = new MagicArmor(armorItem.getMaterial(), armorItem.getType(), new Item.Properties());
        optional = LazyOptional.of(() -> magicArmor);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction)
    {
        if (capability != MAGIC_ARMOR_CAPABILITY) return LazyOptional.empty();

        return optional.cast();
    }
}
