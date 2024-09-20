package net.hm1.auxiliary.setup.registry;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.armor.MagicArmor;
import net.hm1.auxiliary.recipe.MagicArmorUpgradeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

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

    public static class PerkSlots
    {
        public static final PerkSlot NONE = new PerkSlot(new ResourceLocation("ars_nouveau", "zero"), 1000);
    }

    public static final String[] ARMOR_PIECES = { "helmet", "chestplate", "leggings", "boots" };
    public static final int MAX_TIER_ALL = 9;
    public static void postSetup()
    {
        perkArmorsAll();
    }

    private static void perkArmorsAll()
    {
        forArsNouveau();
        forArsElemental();
        forIronsSpellbooksArmor();
        forIceAndFire();
        forOthers();
    }

    public static void forArsNouveau()
    {
        for (var set : new String[]{ "sorcerer", "arcanist", "battlemage"})
        {
            perkArmorPieces("ars_nouveau", set,
                new String[]{ "hood", "robes", "leggings", "boots" },
                MAX_TIER_ALL);
        }
    }

    public static void forArsElemental()
    {
        for (var set : new String[]{ "fire", "air", "earth", "water"})
        {
            perkArmorPieces("ars_nouveau", set,
                new String[]{ "hat", "robes", "leggings", "boots" },
                MAX_TIER_ALL);
        }
    }

    public static void forIronsSpellbooksArmor()
    {
        var sets = new String[] {
            "wandering_magician", "pumpkin", "netherite_mage",
            "pyromancer", "electromancer", "archevoker", "cultist", "cryomancer", "shadowwalker", "priest", "plagued"
        };

        for (var set : sets) {
            perkArmorSet("irons_spellbooks", set, MAX_TIER_ALL);
        }

        perkItems("irons_spellbooks", new String[]{ "tarnished_helmet" }, MAX_TIER_ALL);
    }

    public static void forIceAndFire()
    {
        //
    }

    public static void forOthers()
    {
        perkArmorSet("dreadsteel", "dreadsteel", 9);
    }

    /*
     * Perks armor from mod assuming it has an id format of '{name}_{piece}' (e.g. netherite_chestplate).
     */
    private static void perkArmorSet(String mod, String set, int maxTier)
    {
        for (var piece : ARMOR_PIECES)
        {
            String itemName = String.format("%s_%s", set, piece);
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, itemName));
            if (item == Items.AIR) continue; /// do not perk air !!

            PerkRegistry.registerPerkProvider(item, itemStack -> new ArmorPerkHolder(itemStack, getPerkSlotsForTier(maxTier)));
        }
    }

    private static void perkItems(String mod, String[] items, int tier)
    {
        for (var piece : items)
        {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, piece));
            if (item == Items.AIR) continue; /// do not perk air !!

            PerkRegistry.registerPerkProvider(item, itemStack -> new ArmorPerkHolder(itemStack, getPerkSlotsForTier(tier)));
        }
    }

    /*
     * Perks specific armor pieces from mod.
     */
    private static void perkArmorPieces(String mod, String name, String[] pieces, int tier)
    {
        for (var piece : pieces)
        {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, String.format("%s_%s", name, piece)));
            if (item == Items.AIR) continue; /// do not perk air !!

            PerkRegistry.registerPerkProvider(item, itemStack -> new ArmorPerkHolder(itemStack, getPerkSlotsForTier(tier)));
        }
    }

    private static List<List<PerkSlot>> getPerkSlotsForTier(int tier)
    {
        var perkSlots = new ArrayList<List<PerkSlot>>();

        for (int i = 0; i < tier; i++)
        {
            if (MagicArmor.PERK_SLOTS_TIER_MAPPINGS.containsKey(i + 1))
            {
                perkSlots.add(MagicArmor.PERK_SLOTS_TIER_MAPPINGS.get(i + 1));
            }
        }

        return perkSlots;
    }
}
