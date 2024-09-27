package net.hm1.auxiliary.registry;

import net.hm1.auxiliary.Auxiliary;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Blocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Auxiliary.MOD_ID);

    public static final RegistryObject<Block>
        AUXILIARITE_ALLOY_BLOCK;

    static
    {
        AUXILIARITE_ALLOY_BLOCK = registerBlock("auxiliarite_alloy_block", () ->
            new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK)
                .sound(SoundType.AMETHYST))
        );
    }

    static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> newBlock = BLOCKS.register(name, block);
        registerBlockItem(name, newBlock);
        return newBlock;
    }

    static <T extends Block> void registerBlockItem(String id, RegistryObject<T> block)
    {
        Items.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus event)
    {
        BLOCKS.register(event);
    }
}