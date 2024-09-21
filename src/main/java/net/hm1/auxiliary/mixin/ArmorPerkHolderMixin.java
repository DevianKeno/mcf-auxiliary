package net.hm1.auxiliary.mixin;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import net.hm1.auxiliary.setup.registry.ArsNouveauAuxiliary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
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
    void auxiliary$getSlotsForTier(CallbackInfoReturnable<List<PerkSlot>> cir)
    {
        cir.setReturnValue(new ArrayList<>(ArsNouveauAuxiliary.PerkSlots.TIER_MAPPINGS.get(((ArmorPerkHolder)(Object) this).getTier())));
        cir.cancel();
    }
}
