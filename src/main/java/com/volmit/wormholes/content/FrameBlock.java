package com.volmit.wormholes.content;

import com.volmit.wormholes.utils.SoundUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;

import java.util.concurrent.atomic.AtomicBoolean;

public class FrameBlock extends Block {
    public static final int MAX_PORTAL_RADIUS = 6;
    public static final DirectionProperty FRAME_INNER = DirectionProperty.of("wormdir");
    public static final BooleanProperty FRAME_ACTIVE = BooleanProperty.of("wormset");

    public FrameBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(FRAME_INNER, Direction.UP)
                .with(FRAME_ACTIVE, false));
    }

    public static int sign(int i) {
        return i - MAX_PORTAL_RADIUS;
    }

    public static int unsign(int i) {
        return i + MAX_PORTAL_RADIUS;
    }

    public static boolean isLinked(BlockState state) {
        return state.get(FRAME_ACTIVE);
    }

    public static BlockPos getLinkedPortal(BlockPos p, BlockState b) {
        return new BlockPos(
                p.getX() + b.get(FRAME_INNER).getOffsetX(),
                p.getY() + b.get(FRAME_INNER).getOffsetY(),
                p.getZ() + b.get(FRAME_INNER).getOffsetZ());
    }

    public static BlockState linkPortal(Direction dir, BlockState state) {
        return state.with(FRAME_ACTIVE, true).with(FRAME_INNER, dir);
    }

    public static void breakCheckLogic(ServerWorld level, BlockPos pos, BlockState state) {
        if (!isLinked(state)) {
            return;
        }

        BlockPos id = getLinkedPortal(pos, state);
        AtomicBoolean removed = new AtomicBoolean(false);
        level.getEntitiesByClass(Portal.class, new Box(id).expand(0.5), (f) -> true).forEach((e) -> {
            PortalManipulation.removeConnectedPortals(e, (px) -> {
            });
            removed.set(true);
            e.remove(Entity.RemovalReason.KILLED);
        });

        if (removed.get()) {
            SoundUtil.play(level, new Box(id).getCenter(), SoundEvents.ENTITY_IRON_GOLEM_DEATH, 0.25f, 0.25f);
            SoundUtil.play(level, new Box(id).getCenter(), SoundEvents.BLOCK_CONDUIT_DEACTIVATE, 1f, 0.25f);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FRAME_INNER);
        builder.add(FRAME_ACTIVE);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        breakCheckLogic((ServerWorld) world, pos, state);
        super.onBroken(world, pos, state);
    }
}