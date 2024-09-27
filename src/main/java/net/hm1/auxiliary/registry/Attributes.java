package net.hm1.auxiliary.registry;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.attribute.BulletDamageAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Attributes
{
    public static final DeferredRegister<Attribute> ATTRIBUTES;
    public static final RegistryObject<Attribute>
        BULLET_DAMAGE;

    static
    {
        ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Auxiliary.MOD_ID);

        BULLET_DAMAGE = ATTRIBUTES.register("bullet_damage", BulletDamageAttribute::new);
    }
}
