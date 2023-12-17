package org.aki.melted.common;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.aki.melted.limitedfluid.LimitedFluid;

public class Debug {

    public static boolean isDebugInfoEnabled = false;

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
            CommandManager.literal("setblocksecretly")
            .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .then(CommandManager.argument("blockstate", BlockStateArgumentType.blockState(registryAccess))
                    .then(CommandManager.argument("flags", IntegerArgumentType.integer())
                        .executes(context -> {context.getSource().getWorld().setBlockState(
                            BlockPosArgumentType.getBlockPos(context, "pos"),
                            BlockStateArgumentType.getBlockState(context, "blockstate").getBlockState(),
                            IntegerArgumentType.getInteger(context, "flags")); return 0;}))))));
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(
            CommandManager.literal("getfluidstate")
            .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .executes(context -> getFluidLevel(context))))));
    }

    public static int getFluidLevel(CommandContext<ServerCommandSource> context) {
        BlockPos blockPos = BlockPosArgumentType.getBlockPos(context, "pos");
        FluidState fluidState = context.getSource().getWorld().getFluidState(blockPos);
        if (PublicVars.REFINED_WATER.matchesType(fluidState.getFluid())) {
            context.getSource().sendFeedback(() -> Text.literal(String.format("The level of the limited fluid at (%d,%d,%d) is %d",
                    blockPos.getX(), blockPos.getY(), blockPos.getZ(), fluidState.get(LimitedFluid.ACTUAL_LEVEL))), false);
        } else {
            context.getSource().sendFeedback(() -> Text.literal("It's not a limited fluid."), false);
        }
        return 0;
    }

}
