package net.hm1.auxiliary.items;

import com.tacz.guns.api.DefaultAssets;
import net.hm1.auxiliary.init.ModItems;
import net.hm1.auxiliary.setup.registry.GunsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GunSchematic extends Item
{
    public static final String GUN_ID_TAG = "GunId";
    public static final String RESEARCHED_TAG = "Researched";
    public static final ResourceLocation EMPTY_GUN_ID = DefaultAssets.EMPTY_GUN_ID;

    public GunSchematic()
    {
        super(new Item.Properties());
    }

    public static ItemStack of(String gunId)
    {
        return of(gunId, false);
    }

    public static ItemStack of(String gunId, boolean isResearched)
    {
        ItemStack schem = new ItemStack(ModItems.GUN_SCHEMATIC.get());
        CompoundTag nbt = schem.getOrCreateTag();
        nbt.putString(GUN_ID_TAG, gunId);
        nbt.putBoolean(RESEARCHED_TAG, isResearched);
        return schem;
    }

    public static ItemStack ofRandom(GunsRegistry.GunTypes type)
    {
        return of(GunsRegistry.getRandomGunIdOf(type));
    }

    public static ItemStack ofRandom(GunsRegistry.GunTypes type, boolean isResearched)
    {
        return of(GunsRegistry.getRandomGunIdOf(type), isResearched);
    }

    public static ResourceLocation getGunId(ItemStack stack)
    {
        if (!isGunSchematicItem(stack)) return EMPTY_GUN_ID;

        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(GUN_ID_TAG))
        {
            return new ResourceLocation(nbt.getString(GUN_ID_TAG));
        }
        return EMPTY_GUN_ID;
    }

    public static void setGunId(ItemStack stack, ResourceLocation gunId)
    {
        if (!isGunSchematicItem(stack)) return;
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString(GUN_ID_TAG, gunId.toString());
    }

    public static boolean isResearched(ItemStack stack)
    {
        if (!isGunSchematicItem(stack)) return false;
        return stack.getOrCreateTag().getBoolean(RESEARCHED_TAG);
    }

    public static void setResearched(ItemStack stack, boolean isResearched)
    {
        if (!isGunSchematicItem(stack)) return;
        stack.getOrCreateTag().putBoolean(RESEARCHED_TAG, isResearched);
    }

    static boolean isGunSchematicItem(ItemStack stack)
    {
        return stack.getItem() == ModItems.GUN_SCHEMATIC.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags)
    {
        ResourceLocation gunId = getGunId(stack);
        tooltip.add(Component.literal(gunId.toString()).withStyle(ChatFormatting.GOLD));

        if (isResearched(stack)) {
            tooltip.add(Component.translatable("auxiliary.gun_schematic_researched").withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.add(Component.translatable("auxiliary.gun_schematic_unresearched").withStyle(ChatFormatting.RED));
        }

        super.appendHoverText(stack, world, tooltip, flags);
    }
}
