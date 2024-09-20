package net.hm1.auxiliary.datagen;

import net.hm1.auxiliary.Auxiliary;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider
{
    public ModPoiTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper)
    {
        super(packOutput, provider, Auxiliary.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).addOptional(new ResourceLocation(Auxiliary.MOD_ID, "gun_workbench_poi"));
    }
}
