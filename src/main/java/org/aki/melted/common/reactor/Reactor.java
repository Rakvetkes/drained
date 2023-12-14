package org.aki.melted.common.reactor;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface Reactor {
    void exchange(World world, BlockPos pos, Direction direction);

}
