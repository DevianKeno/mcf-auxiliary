package net.hm1.auxiliary.datagen.loot;

import java.util.Set;
import java.util.HashSet;
import net.hm1.auxiliary.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;


public class ModBlockLoot extends BlockLootSubProvider
{
    private final Set<Block> generatedLootTables = new HashSet<>();

    public ModBlockLoot()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate()
    {
        dropSelf(ModBlocks.AUXILIARITE_ALLOY_BLOCK.get());
        dropSelf(ModBlocks.GUN_WORKBENCH.get());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}
