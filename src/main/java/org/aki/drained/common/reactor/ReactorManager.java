package org.aki.drained.common.reactor;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.aki.drained.common.util.Pair;

public class ReactorManager {

    public static ReactorManager INSTANCE = new ReactorManager();

    protected Object2ObjectMap<Pair<Fluid, Fluid>, Reactor> reactorManager;

    protected ReactorManager() {
        reactorManager = new Object2ObjectOpenHashMap<>();
    }

    public void call(World world, BlockPos pos, Direction direction) {
        BlockPos targetPos = pos.offset(direction);
        Fluid fluid1 = world.getFluidState(pos).getFluid();
        Fluid fluid2 = world.getFluidState(targetPos).getFluid();
        Reactor reactor = reactorManager.get(new Pair<>(fluid1, fluid2));
        reactor.exchange(world, pos, direction);
    }

    public void register(Fluid fluid1, Fluid fluid2, Reactor reactor) {
        reactorManager.put(new Pair<>(fluid1, fluid2), reactor);
    }

    public void register(Fluid fluid, Reactor reactor) {
        register(fluid, fluid, reactor);
    }

    public void registerExchangeable(Fluid fluid1, Fluid fluid2, Reactor reactor) {
        register(fluid1, fluid2, reactor);
        if (fluid1 != fluid2) {
            register(fluid2, fluid1, (world, pos, direction) -> {
                reactor.exchange(world, pos.offset(direction), direction.getOpposite());
            });
        }
    }

}
