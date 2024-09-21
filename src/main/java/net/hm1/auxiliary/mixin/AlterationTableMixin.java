package net.hm1.auxiliary.mixin;

import com.hollingsworth.arsnouveau.api.perk.*;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.block.AlterationTable;
import com.hollingsworth.arsnouveau.common.block.tile.AlterationTile;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin({AlterationTable.class})
public abstract class AlterationTableMixin
{
    public AlterationTableMixin(){}

    @Redirect(
        remap = false,
        method = "use",
        at = @At(
            value = "INVOKE",
            target = "Lcom/hollingsworth/arsnouveau/common/block/tile/AlterationTile;addPerkStack(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)V")
    )
    private void auxiliary$redirectAddPerkStack(AlterationTile tile, ItemStack stack, Player player)
    {
        IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(tile.armorStack);
        ///                       vvvvvvvvvvvvvvv replaced StackPerkHolder -> ArmorPerkHolder
        if (perkHolder instanceof ArmorPerkHolder armorPerkHolder) {
            if (armorPerkHolder.getTier() == 0) {
                PortUtil.sendMessage(player, Component.translatable("ars_nouveau.not_threaded"));
            } else if (tile.perkList.size() < 3 && tile.perkList.size() < armorPerkHolder.getSlotsForTier().size()) {
                PerkSlot foundSlot = tile.perkList.size() >= perkHolder.getSlotsForTier().size() ? null : perkHolder.getSlotsForTier().get(tile.perkList.size()); /// COPIED FROM
                Item item = stack.getItem();

                if (item instanceof PerkItem perkItem) {
                    IPerk perk = perkItem.perk;

                    if (foundSlot != null && perk.validForSlot(foundSlot, tile.armorStack, player)) {
                        tile.perkList.add(stack.split(1));
                        if (tile.newPerkTimer <= 0) {
                            tile.newPerkTimer = 40;
                        }

                        tile.updateBlock();
                    }
                }
            } else {
                PortUtil.sendMessage(player, Component.translatable("ars_nouveau.perk.max_perks"));
            }
        } else {
            PortUtil.sendMessage(player, Component.translatable("ars_nouveau.perk.set_armor"));
        }
    }
}
