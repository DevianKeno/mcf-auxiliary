package net.hm1.auxiliary.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.*;

import java.util.*;
import java.util.stream.Collectors;

import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.setup.registry.ArsNouveauAuxiliary;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicArmor
{
    static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = new EnumMap<>(ArmorItem.Type.class);
    static {
        ARMOR_MODIFIER_UUID_PER_TYPE.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        ARMOR_MODIFIER_UUID_PER_TYPE.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        ARMOR_MODIFIER_UUID_PER_TYPE.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        ARMOR_MODIFIER_UUID_PER_TYPE.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    }

    //region Registry
    public static final List<String> ALL_IDS = getAllMagicArmorIds();
    public static List<String> getAllMagicArmorIds()
    {
        var list = new ArrayList<String>();

        for (var set : ArsNouveauAuxiliary.MAGIC_ARMOR_SET) {
            for (var piece : List.of("hood", "robes", "leggings", "boots")) {
                list.add(formatArmorId("ars_nouveau", set, piece));
            }}

        for (var set : ArsNouveauAuxiliary.ELEMENTAL_ARMOR_SET) {
            for (var piece : List.of("hat", "robes", "leggings", "boots")) {
                list.add(formatArmorId("ars_elemental", set, piece));
            }}

        for (var set : Auxiliary.IRONS_SPELLBOOKS_ARMOR_SET) {
            list.addAll(getArmorSetIds("irons_spellbooks", set, Auxiliary.ARMOR_PIECES));
        }

        for (var set : Auxiliary.IMMERSIVE_ARMORS_SET) {
            list.addAll(getArmorSetIds("immersive_armors", set, Auxiliary.ARMOR_PIECES));
        }

        return list;
    }

    public static String formatArmorId(String mod, String material, String piece) {
        return String.format("%s:%s_%s", mod, material, piece);
    }

    public static List<String> getArmorSetIds(String mod, String set, String[] armorPieces) {
        return Arrays.stream(armorPieces).map(p -> String.format("%s:%s_%s", mod, set, p)).collect(Collectors.toList());
    }
    //endregion
}

