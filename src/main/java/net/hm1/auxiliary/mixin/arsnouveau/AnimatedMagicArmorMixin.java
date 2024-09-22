package net.hm1.auxiliary.mixin.arsnouveau;

import com.hollingsworth.arsnouveau.common.armor.AnimatedMagicArmor;
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
 * Cancels tooltips to give way for Threaded tooltips
 */
@Mixin({AnimatedMagicArmor.class})
public class AnimatedMagicArmorMixin
{
    public AnimatedMagicArmorMixin(){}

    @Inject(
        method = {"appendHoverText"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false)
    public void auxiliary$appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci)
    {
        ci.cancel();
    }
}
