package net.hm1.auxiliary.mixin.ironsspellbooks;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
 * Adds Spell Power from Ars Nouveau for Iron's Spells
 */
@Mixin({AbstractSpell.class})
public class AbstractSpellMixin
{
    public AbstractSpellMixin(){};

    /*
     * Currently this just adds the AN Spell Power to the final damage of an Iron's Spell
     */
    @Inject(
        method = "getSpellPower",
        at = @At("RETURN"),
        cancellable = true,
        remap = false)
    public void auxiliary$getSpellPower(int spellLevel, Entity sourceEntity, CallbackInfoReturnable<Float> cir)
    {
        // Note: Method is named getSpellPower() but will actually return the damage value
        if (sourceEntity == null) return;
        var etty = (LivingEntity) sourceEntity;
        var anAttr = com.hollingsworth.arsnouveau.api.perk.PerkAttributes.SPELL_DAMAGE_BONUS.get();
        var ironsAttr = io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER.get();

        if (etty.getAttributes().hasAttribute(anAttr))
        {
            double flatBonus = etty.getAttributeValue(anAttr);
            double multiplier = 1.0d;
            if (etty.getAttributes().hasAttribute(ironsAttr))
            {
                multiplier = etty.getAttributeValue(ironsAttr);
            }
            float damage = cir.getReturnValue();
            cir.setReturnValue((float) ((damage * multiplier) + (flatBonus * multiplier)));
        }
    }
}
