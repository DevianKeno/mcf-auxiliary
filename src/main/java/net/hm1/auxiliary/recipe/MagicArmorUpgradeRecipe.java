package net.hm1.auxiliary.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ITextOutput;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import net.hm1.auxiliary.setup.registry.ArsNouveauAuxiliary;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MagicArmorUpgradeRecipe extends EnchantingApparatusRecipe implements ITextOutput
{
    public int tier;

    public MagicArmorUpgradeRecipe(List<Ingredient> pedestalItems, int cost, int tier)
    {
        this(new ResourceLocation("ars_nouveau", "upgrade_" + tier), pedestalItems, cost, tier);
    }

    public MagicArmorUpgradeRecipe(ResourceLocation id, List<Ingredient> pedestalItems, int cost, int tier) {
        this.id = id;
        this.pedestalItems = pedestalItems;
        this.sourceCost = cost;
        this.tier = tier;
    }

    public JsonElement asRecipe()
    {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", "ars_nouveau:armor_upgrade");
        jsonobject.addProperty("sourceCost", this.getSourceCost());
        JsonArray pedestalArr = new JsonArray();

        for (Ingredient i : this.pedestalItems) {
            JsonObject object = new JsonObject();
            object.add("item", i.toJson());
            pedestalArr.add(object);
        }

        jsonobject.add("pedestalItems", pedestalArr);
        jsonobject.addProperty("tier", this.tier);
        return jsonobject;
    }

    public boolean doesReagentMatch(ItemStack reag)
    {
        return true;
    }

    public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @Nullable Player player) {
        IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(reagent);
        if (!(perkHolder instanceof ArmorPerkHolder armorPerkHolder)) {
            return false;
        } else {
            return armorPerkHolder.getTier() == this.tier - 1 && super.isMatch(pedestalItems, reagent, enchantingApparatusTile, player);
        }
    }

    public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile)
    {
        if (PerkUtil.getPerkHolder(reagent) instanceof ArmorPerkHolder armorPerkHolder)
        {
            armorPerkHolder.setTier(this.tier);
        }
        return reagent.copy();
    }

    public RecipeType<?> getType() { return ArsNouveauAuxiliary.Registry.MAGIC_ARMOR_UPGRADE_RECIPES.get(); }
    public Component getOutputComponent() { return Component.translatable("ars_nouveau.armor_upgrade.book_desc", new Object[]{this.tier}); }
    public boolean excludeJei() { return true; }

    public RecipeSerializer<?> getSerializer() { return ArsNouveauAuxiliary.Registry.MAGIC_ARMOR_UPGRADE_SERIALIZER.get(); }

    public static class Serializer implements RecipeSerializer<MagicArmorUpgradeRecipe>
    {
        public Serializer(){}

        public MagicArmorUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            int cost = json.has("sourceCost") ? GsonHelper.getAsInt(json, "sourceCost") : 0;
            int tier = json.has("tier") ? GsonHelper.getAsInt(json, "tier") : 0;
            JsonArray pedestalItems = GsonHelper.getAsJsonArray(json, "pedestalItems");
            List<Ingredient> stacks = new ArrayList();

            Ingredient input;
            for (Iterator var7 = pedestalItems.iterator(); var7.hasNext(); stacks.add(input)) {
                JsonElement e = (JsonElement) var7.next();
                JsonObject obj = e.getAsJsonObject();
                if (GsonHelper.isArrayNode(obj, "item")) {
                    input = Ingredient.fromJson(GsonHelper.getAsJsonArray(obj, "item"));
                } else {
                    input = Ingredient.fromJson(GsonHelper.getAsJsonObject(obj, "item"));
                }
            }

            return new MagicArmorUpgradeRecipe(recipeId, stacks, cost, tier);
        }

        @Nullable
        public MagicArmorUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            int length = buffer.readInt();
            List<Ingredient> stacks = new ArrayList<>();

            int cost;
            for (cost = 0; cost < length; ++cost) {
                try {
                    stacks.add(Ingredient.fromNetwork(buffer));
                } catch (Exception var7) {
                    var7.printStackTrace();
                    break;
                }
            }

            cost = buffer.readInt();
            int tier = buffer.readInt();
            return new MagicArmorUpgradeRecipe(recipeId, stacks, cost, tier);
        }

        public void toNetwork(FriendlyByteBuf buf, MagicArmorUpgradeRecipe recipe)
        {
            buf.writeInt(recipe.pedestalItems.size());
            buf.writeItem(recipe.result);

            for (Ingredient i : recipe.pedestalItems) {
                i.toNetwork(buf);
            }

            buf.writeInt(recipe.sourceCost);
            buf.writeInt(recipe.tier);
        }
    }
}
