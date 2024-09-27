package net.hm1.auxiliary.registry;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.enchantment.BallisticsEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Enchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Auxiliary.MOD_ID);
    public static final RegistryObject<Enchantment>
        BALLISTICS;

    static
    {
        BALLISTICS = ENCHANTMENTS.register("ballistics", BallisticsEnchantment::new);
    }

    public static void register(IEventBus event)
    {
        ENCHANTMENTS.register(event);
    }
}
