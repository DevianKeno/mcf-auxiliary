package net.hm1.auxiliary.mixin;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import net.hm1.auxiliary.armor.MagicArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin({ArmorPerkHolder.class})
public class ArmorPerkHolderMixin
{
    public ArmorPerkHolderMixin(){}

    @Inject(
        method = {"getSlotsForTier"},
        at = {@At("HEAD")},
        cancellable = true,
        remap = false
    )
    private void auxiliary$getSlotsForTier(CallbackInfoReturnable<List<PerkSlot>> cir)
    {
        int tier = ((ArmorPerkHolder)(Object)this).getTier();

        if (MagicArmor.PERK_SLOTS_TIER_MAPPINGS.containsKey(tier))
        {
            cir.setReturnValue(MagicArmor.PERK_SLOTS_EMPTY);
        }
        else
        {
            cir.setReturnValue(MagicArmor.PERK_SLOTS_TIER_MAPPINGS.get(tier));
        }

        cir.cancel();
    }
}
