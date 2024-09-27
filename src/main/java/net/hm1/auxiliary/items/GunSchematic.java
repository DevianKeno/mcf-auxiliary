package net.hm1.auxiliary.items;

import com.tacz.guns.api.TimelessAPI;
import net.hm1.auxiliary.registry.Items;
import net.hm1.auxiliary.setup.registry.GunsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class GunSchematic extends Item
{
    public static final String GUN_ID_TAG = "GunId";
    public static final String RESEARCHED_TAG = "Researched";
    public static final ResourceLocation EMPTY_GUN_ID = new ResourceLocation("auxiliary", "empty");;

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
        ItemStack schem = new ItemStack(Items.GUN_SCHEMATIC.get());
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
        if (nbt.contains(GUN_ID_TAG)) {
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
        return stack.getItem() == Items.GUN_SCHEMATIC.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags)
    {
        String gunName = GunsRegistry.UNKNOWN_GUN;
        var resourceLocation = getGunId(stack);
        if (resourceLocation.getNamespace().equals(com.tacz.guns.GunMod.MOD_ID)) {
            var gunIndex = TimelessAPI.getClientGunIndex(resourceLocation);
            if (gunIndex.isPresent()) gunName = gunIndex.get().getName();
        } else if (resourceLocation.getNamespace().equals(com.vicmatskiv.pointblank.PointBlankMod.MODID)) {
            var item = ForgeRegistries.ITEMS.getValue(resourceLocation);
            if (item instanceof com.vicmatskiv.pointblank.item.GunItem pbg) {
                gunName = String.format("item.pointblank.%s", pbg.getName());
            }
        }
        tooltip.add(Component.translatable(gunName).withStyle(ChatFormatting.GOLD));

        if (isResearched(stack)) {
            tooltip.add(Component.translatable("auxiliary.gun_schematic_researched").withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.add(Component.translatable("auxiliary.gun_schematic_unresearched").withStyle(ChatFormatting.RED));
        }

        super.appendHoverText(stack, world, tooltip, flags);
    }
}
