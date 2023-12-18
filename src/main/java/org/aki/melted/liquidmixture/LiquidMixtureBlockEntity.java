package org.aki.melted.liquidmixture;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.aki.melted.common.PublicVars;
import org.jetbrains.annotations.Nullable;

public class LiquidMixtureBlockEntity extends BlockEntity {

    private Object2IntLinkedOpenHashMap<SubstanceUnit> component;

    public LiquidMixtureBlockEntity(BlockPos pos, BlockState state) {
        super(PublicVars.LIQUID_MIXTURE_BLOCK_ENTITY, pos, state);
        component = new Object2IntLinkedOpenHashMap<>();
    }

    @Override
    public void writeNbt(NbtCompound nbtCompound) {
        super.writeNbt(nbtCompound);
        nbtCompound.putString("component", serialize());
    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {
        super.readNbt(nbtCompound);
        deserialize(nbtCompound.getString("component"));
    }

    protected String serialize() {
        String result = new String();
        for (SubstanceUnit s : component.keySet()) {
            int content = component.getInt(s);
            result += SubstanceRegistry.INSTANCE.getName(s);
            result += ',';
            result += String.format("%d", content);
            result += ';';
        }
        return result;
    }

    protected void deserialize(String s) {
        component.clear();
        int startingPos = 0, endingPos = s.indexOf(';');
        while (endingPos != -1) {
            int separatorPos = s.indexOf(',', startingPos);
            String componentName = s.substring(startingPos, separatorPos);
            int content = Integer.parseInt(s.substring(separatorPos + 1, endingPos));
            component.put(SubstanceRegistry.INSTANCE.get(componentName), content);
            startingPos = endingPos + 1;
            endingPos = s.indexOf(';', startingPos);
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}
