package net.hm1.auxiliary.init;

import net.hm1.auxiliary.Auxiliary;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> AUXILIARY_CREATIVE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Auxiliary.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        AUXILIARY_CREATIVE_TAB.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> AUXILIARY_TAB =
        AUXILIARY_CREATIVE_TAB.register("auxiliary_creative_tab", () ->
            CreativeModeTab.builder()
                .title(Component.translatable("creativetab.auxiliary_creative_tab"))
                .icon(() -> new ItemStack(ModItems.AUXILIARITE_ALLOY.get()))
                .displayItems((pParameters, pOutput) ->
                {
                    for (RegistryObject<Item> item : ModItems.ITEMS.getEntries())
                    {
                        pOutput.accept(item.get());
                    }
                })
                .build());
}
