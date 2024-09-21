package net.hm1.auxiliary.setup.registry;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.armor.MagicArmor;
import net.hm1.auxiliary.recipe.MagicArmorUpgradeRecipe;
import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArsNouveauAuxiliary
{
    public static class Registry
    {
        public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES;
        public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;

        public static final String MAGIC_ARMOR_UPGRADE_RECIPE_ID = "magic_armor_upgrade";
        public static final RegistryObject<RecipeType<MagicArmorUpgradeRecipe>> MAGIC_ARMOR_UPGRADE_RECIPES;
        public static final RegistryObject<RecipeSerializer<MagicArmorUpgradeRecipe>> MAGIC_ARMOR_UPGRADE_SERIALIZER;

        static
        {
            RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Auxiliary.MOD_ID);
            RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Auxiliary.MOD_ID);

            MAGIC_ARMOR_UPGRADE_RECIPES = RECIPE_TYPES.register(MAGIC_ARMOR_UPGRADE_RECIPE_ID, ModRecipeType::new);
            MAGIC_ARMOR_UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register(MAGIC_ARMOR_UPGRADE_RECIPE_ID, MagicArmorUpgradeRecipe.Serializer::new);
        }

        public Registry(){}

        public static void register(IEventBus bus)
        {
            RECIPE_TYPES.register(bus);
            RECIPE_SERIALIZERS.register(bus);
        }

        private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
            private ModRecipeType(){}

            public String toString() { return ForgeRegistries.RECIPE_TYPES.getKey(this).toString(); }
        }
    }

    public static String[] MAGIC_ARMOR_SET = { "sorcerer", "arcanist", "battlemage" };
    public static String[] ELEMENTAL_ARMOR_SET = { "fire", "air", "earth", "aqua" };

    public static class PerkSlots
    {
        public static final PerkSlot NONE = new PerkSlot(new ResourceLocation("ars_nouveau", "zero"), 1000);
        public static final List<PerkSlot> SLOTS_EMPTY = List.of();
        public static final List<PerkSlot> SLOTS_T1 = List.of(PerkSlot.ONE);
        public static final List<PerkSlot> SLOTS_T2 = List.of(PerkSlot.ONE, PerkSlot.ONE);
        public static final List<PerkSlot> SLOTS_T3 = List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.ONE);
        public static final List<PerkSlot> SLOTS_T4 = List.of(PerkSlot.ONE, PerkSlot.ONE, PerkSlot.TWO);
        public static final List<PerkSlot> SLOTS_T5 = List.of(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.TWO);
        public static final List<PerkSlot> SLOTS_T6 = List.of(PerkSlot.TWO, PerkSlot.TWO, PerkSlot.TWO);
        public static final List<PerkSlot> SLOTS_T7 = List.of(PerkSlot.TWO, PerkSlot.TWO, PerkSlot.THREE);
        public static final List<PerkSlot> SLOTS_T8 = List.of(PerkSlot.TWO, PerkSlot.THREE, PerkSlot.THREE);
        public static final List<PerkSlot> SLOTS_T9 = List.of(PerkSlot.THREE, PerkSlot.THREE, PerkSlot.THREE);

        // Key is tier, Value is Perk Slots
        public static final Map<Integer, List<PerkSlot>> TIER_MAPPINGS = new HashMap<>() {{
            put(0, SLOTS_EMPTY);
            put(1, SLOTS_T1);
            put(2, SLOTS_T2);
            put(3, SLOTS_T3);
            put(4, SLOTS_T4);
            put(5, SLOTS_T5);
            put(6, SLOTS_T6);
            put(7, SLOTS_T7);
            put(8, SLOTS_T8);
            put(9, SLOTS_T9);
        }};

    }

    public static final int MAX_TIER_ALL = 9;
    public static void postSetup()
    {
        if (AuxiliaryConfig.MagicArmorConfig.MAGIC_ARMOR_IS_THREADABLE.get())
        {
            perkItemList(AuxiliaryConfig.MagicArmorConfig.MAGIC_ARMOR_ITEMS.get(), ArsNouveauAuxiliary.MAX_TIER_ALL);
        }
    }

    public static void perkItemList(List<? extends String> ids, int tier)
    {
        for (var id : ids)
        {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
            if (item == null || item == Items.AIR) continue; /// do not perk air !!
            PerkRegistry.registerPerkProvider(item, itemStack -> new ArmorPerkHolder(itemStack, getAllPerkSlotsForTier(tier)));
        }
    }

    /*
     * Perks armor from mod assuming it has an id format of '{name}_{piece}' (e.g. netherite_chestplate).
     */
    public static void perkArmorSet(String mod, String set, int maxTier)
    {
        for (var piece : Auxiliary.ARMOR_PIECES)
        {
            String itemName = String.format("%s_%s", set, piece);
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, itemName));
            if (item == null || item == Items.AIR) continue; /// do not perk air !!
            PerkRegistry.registerPerkProvider(item, itemStack -> new ArmorPerkHolder(itemStack, getAllPerkSlotsForTier(maxTier)));
        }
    }

    /*
     * Perks armor appending given pieces to the end.
     * e.g. "armor_head"
     */
    public static void perkArmorPieces(String id, String[] pieces, int tier)
    {
        for (var piece : pieces)
        {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(String.format("%s_%s", id, piece)));
            if (item == null || item == Items.AIR) continue; /// do not perk air !!
            PerkRegistry.registerPerkProvider(item, itemStack -> new ArmorPerkHolder(itemStack, getAllPerkSlotsForTier(tier)));
        }
    }

    public static List<List<PerkSlot>> getAllPerkSlotsForTier(int maxTier)
    {
        var perkSlots = new ArrayList<List<PerkSlot>>();
        for (int i = 0; i < maxTier; i++)
        {
            if (ArsNouveauAuxiliary.PerkSlots.TIER_MAPPINGS.containsKey(i))
            {
                perkSlots.add(ArsNouveauAuxiliary.PerkSlots.TIER_MAPPINGS.get(i));
            }
        }
        return perkSlots;
    }
}
