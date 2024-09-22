package net.hm1.auxiliary.items;

import javax.annotation.Nullable;
import java.util.List;

import net.hm1.auxiliary.init.ModTiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class FulcaligrItem extends SwordItem
{
    public FulcaligrItem()
    {
        super(
            ModTiers.AUXILIARITE,
            46,
            -2.4f,
            new Item.Properties()
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
            player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pFlags)
    {
        pTooltipComponents.add(Component.translatable(
            "tooltip.auxiliary.fulcaligr.tooltip"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pFlags);
    }
}
