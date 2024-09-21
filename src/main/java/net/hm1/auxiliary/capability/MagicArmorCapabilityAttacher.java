package net.hm1.auxiliary.capability;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import net.hm1.auxiliary.armor.MagicArmor;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicArmorCapabilityAttacher
{
    public MagicArmorCapabilityAttacher(){}
    public static void attach(AttachCapabilitiesEvent<ItemStack> item)
    {
        item.addCapability(MagicArmorCapabilityProvider.IDENTIFIER, new MagicArmorCapabilityProvider());
    }

    public static final Capability<IMagicArmorCapability> MAGIC_ARMOR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private static class MagicArmorCapabilityProvider implements ICapabilityProvider
    {
        private MagicArmorCapabilityProvider(){}
        public static final ResourceLocation IDENTIFIER = new ResourceLocation("auxiliary", "magic_armor");

        private final IMagicArmorCapability magicArmor = new MagicArmor();
        private final LazyOptional<IMagicArmorCapability> optional = LazyOptional.of(() -> magicArmor);

        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return MAGIC_ARMOR_CAPABILITY.orEmpty(cap, this.optional);
        }

        void invalidate() {
            this.optional.invalidate();
        }
    }
}
