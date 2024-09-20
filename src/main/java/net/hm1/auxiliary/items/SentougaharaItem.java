package net.hm1.auxiliary.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SentougaharaItem extends SwordItem
{
    public SentougaharaItem()
    {
        super(
            ModTiers.AUXILIARITE,
            46,
            3.5f,
            new Properties()
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
            "tooltip.auxiliary.sentougahara.tooltip"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pFlags);
    }
}
