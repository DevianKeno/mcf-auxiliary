package net.hm1.auxiliary.mixin.pointblank;

import com.vicmatskiv.pointblank.attachment.Attachment;
import com.vicmatskiv.pointblank.client.ClientEventHandler;
import com.vicmatskiv.pointblank.item.GunItem;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

/*
 * Overrides default attachments so all guns have no attachments when crafted :)
 */
@Mixin({GunItem.Builder.class})
public class GunItemBuilderMixin
{
    public GunItemBuilderMixin(){}

    @Inject(
        method = "withDefaultAttachment",
        at = @At(value = "HEAD"),
        cancellable = true,
        remap = false)
    private void auxiliary$overrideDefaultAttachments(Supplier<? extends Attachment>[] attachmentSuppliers, CallbackInfoReturnable<GunItem.Builder> cir)
    {
        boolean cancel = AuxiliaryConfig.NO_ATTACHMENTS_ON_CRAFTED_GUNS.get();
        cir.setReturnValue((GunItem.Builder)(Object) this);
        if (cancel) cir.cancel();
    }
}