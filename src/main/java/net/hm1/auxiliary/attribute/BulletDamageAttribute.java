package net.hm1.auxiliary.attribute;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class BulletDamageAttribute extends RangedAttribute
{
    public BulletDamageAttribute()
    {
        super("Damage modifier for bullets.", 100d, 0d, 1000d);
    }
}
