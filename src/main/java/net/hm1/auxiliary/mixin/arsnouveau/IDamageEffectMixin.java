//package net.hm1.auxiliary.mixin.arsnouveau;
//
//import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
//import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
//import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attribute;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin({IDamageEffect.class})
//public class IDamageEffectMixin
//{
//    public IDamageEffectMixin(){};
//
//    /*
//     * Assuming the player always has an AN Spell Power Attribute :D
//     */
//    @Redirect(
//        method = "attemptDamage",
//        at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"),
//        remap = false)
//    public double auxiliary$attemptDamage(LivingEntity entity, Attribute pAttribute)
//    {
//        var an_spellPower = entity.getAttributeValue(PerkAttributes.SPELL_DAMAGE_BONUS.get());
//        double irons_modifier = 1.0f;
//        if (entity.getAttributes().hasAttribute(AttributeRegistry.SPELL_POWER.get())) {
//            irons_modifier = entity.getAttributeValue(AttributeRegistry.SPELL_POWER.get());
//        }
//        return an_spellPower * irons_modifier;
//    }
//}
