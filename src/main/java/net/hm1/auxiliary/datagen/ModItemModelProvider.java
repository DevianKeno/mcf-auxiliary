package net.hm1.auxiliary.datagen;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, Auxiliary.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        simpleItem(ModItems.AUXILIARITE_ALLOY);
        simpleItem(ModItems.CATACLYSMIC_AMALGAMATION);
        simpleItem(ModItems.DESTRUCTION_CATALYST);
        simpleItem(ModItems.STALWART_CRYSTAL);
        gunSchematicItem(ModItems.GUN_SCHEMATIC);

        handheldItem(ModItems.FULCALIGR);
        handheldItem(ModItems.SENTOUGAHARA);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
            new ResourceLocation("item/generated")).texture("layer0",
            new ResourceLocation(Auxiliary.MOD_ID,"item/" + item.getId().getPath()));
    }

    private void gunSchematicItem(RegistryObject<Item> item)
    {
        String itemPath = item.getId().getPath();

        withExistingParent(itemPath, new ResourceLocation("item/generated"))
            .texture("layer0", new ResourceLocation(Auxiliary.MOD_ID, "item/unresearched_gun_schematic"))
            .override()
            .predicate(new ResourceLocation("custom_model_data"), 1)
            .model(withExistingParent(itemPath + "_researched", new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Auxiliary.MOD_ID, "item/researched_gun_schematic")))
            .end();
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
            new ResourceLocation("item/handheld")).texture("layer0",
            new ResourceLocation(Auxiliary.MOD_ID,"item/" + item.getId().getPath()));
    }
}
