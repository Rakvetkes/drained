package org.aki.melted.mixture;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.aki.melted.PublicVars;

public class MixtureDescription extends BlockEntity {

    public MixtureDescription(BlockPos pos, BlockState state) {
        super(PublicVars.MIXTURE_DESCRIPTION, pos, state);
    }

}
