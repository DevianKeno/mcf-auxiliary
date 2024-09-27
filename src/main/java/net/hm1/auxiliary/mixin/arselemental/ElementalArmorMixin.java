package net.hm1.auxiliary.mixin.arselemental;

import alexthw.ars_elemental.client.TooltipUtils;
import alexthw.ars_elemental.common.items.armor.ElementalArmor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/*
 * Like on the AnimatedMagicArmorMixin, cancels tooltips to give way for Threaded tooltips
 */
@Mixin({ElementalArmor.class})
public abstract class ElementalArmorMixin
{
    public ElementalArmorMixin(){}

    @Inject(
        method = "appendHoverText",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void auxiliary$cancelTooltipButNotShift(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags, CallbackInfo ci)
    {
        TooltipUtils.addOnShift(tooltip, () ->{
            ((ElementalArmor)(Object) this).addInformationAfterShift(stack, world, tooltip, flags);
        }, "armor_set");

        ci.cancel();
    }
}
