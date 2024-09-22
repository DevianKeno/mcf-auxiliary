package net.hm1.auxiliary.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.items.GunSchematic;
import net.hm1.auxiliary.setup.registry.GunsRegistry.GunTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class ModCommands
{
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        register(event.getDispatcher());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        registerGunSchematic(dispatcher, "auxiliary");
    }

    private static void registerGunSchematic(CommandDispatcher<CommandSourceStack> dispatcher, String literal)
    {
        dispatcher.register(Commands.literal(literal)
            .then(Commands.literal("gunschematic")
                .then(Commands.argument("gunId", StringArgumentType.string())
                    .then(Commands.argument("isResearched", BoolArgumentType.bool()))
                        .executes(context -> giveGunSchematicById(context.getSource(), StringArgumentType.getString(context, "gunId"), BoolArgumentType.getBool(context, "isResearched"))))

                .then(Commands.literal("random")
                    .then(Commands.argument("gunType", StringArgumentType.string())
                        .then(Commands.argument("isResearched", BoolArgumentType.bool()))
                            .executes(context -> giveRandomGunSchematic(context.getSource(), StringArgumentType.getString(context, "gunType"), BoolArgumentType.getBool(context, "isResearched")))))));
    }

    private static int giveGunSchematicById(CommandSourceStack source, String gunId, boolean isResearched) throws CommandSyntaxException
    {
        var player = source.getPlayerOrException();
        var schematic = GunSchematic.of(gunId, isResearched);

        if (schematic.getItem() != Items.AIR) {
            player.addItem(schematic);
            source.sendSuccess(() -> Component.translatable("command.auxiliary.gunschematic.success"), true);
            return Command.SINGLE_SUCCESS;
        } else {
            source.sendFailure(Component.literal("Gun ID not found: " + gunId));
            return 0;
        }
    }

    private static int giveRandomGunSchematic(CommandSourceStack source, String gunType, boolean isResearched) throws CommandSyntaxException
    {
        var player = source.getPlayerOrException();
        var type = getGunTypeFromString(gunType.toLowerCase());
        if (type == null) {
            source.sendFailure(Component.translatable("command.auxiliary.gunschematic.invalid_type"));
            return 0;
        }

        var schematic = GunSchematic.ofRandom(type, isResearched);
        if (schematic.getItem() != Items.AIR) {
            player.addItem(schematic);
            source.sendSuccess(() -> Component.translatable("command.auxiliary.gunschematic.success"), true);
            return Command.SINGLE_SUCCESS;
        } else {
            source.sendFailure(Component.translatable("command.auxiliary.gunschematic.not_found"));
            return 0;
        }
    }


    private static GunTypes getGunTypeFromString(String gunType) {
        return switch (gunType) {
            case "handgun" -> GunTypes.HANDGUN;
            case "smg" -> GunTypes.SUBMACHINE_GUN;
            case "sg" -> GunTypes.SHOTGUN;
            case "ar" -> GunTypes.ASSAULT_RIFLE;
            case "sr" -> GunTypes.SNIPER_RIFLE;
            case "dmr" -> GunTypes.DESIGNATED_MARKSMAN_RIFLE;
            case "mg" -> GunTypes.MACHINE_GUN;
            case "special" -> GunTypes.SPECIAL;
            default -> null;
        };
    }
}
