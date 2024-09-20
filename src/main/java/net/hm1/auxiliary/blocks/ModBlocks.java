package net.hm1.auxiliary.blocks;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Auxiliary.MOD_ID);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> newBlock = BLOCKS.register(name, block);
        registerBlockItem(name, newBlock);

        return newBlock;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String id, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static final RegistryObject<Block> AUXILIARITE_ALLOY_BLOCK = registerBlock("auxiliarite_alloy_block",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
            .sound(SoundType.AMETHYST))
    );

    public static final RegistryObject<Block> GUN_WORKBENCH = registerBlock("gun_workbench",
        () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0F, 3.0F)
            .sound(SoundType.WOOD)
        )
    );
}
