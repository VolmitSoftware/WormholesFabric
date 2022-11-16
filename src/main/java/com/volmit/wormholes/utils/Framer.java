package com.volmit.wormholes.utils;

import com.volmit.wormholes.content.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Framer {
    private final Set<BlockPos> positionsFound = new HashSet<>();
    private final BlockPos cursor;
    private final World level;
    List<BlockPos> check = List.of(
            new BlockPos(1, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, 1, 0),
            new BlockPos(0, -1, 0),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -1)
    );
    private boolean done = false;

    public Framer(World level, BlockPos cursor) {
        this.level = level;
        this.cursor = cursor;
    }

    public Cuboid validate() {
        tick(cursor, 128);

        if (!done) {
            return null;
        }

        BlockPos min = new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        BlockPos max = new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (BlockPos i : positionsFound) {
            max = new BlockPos(Math.max(max.getX(), i.getX()), Math.max(max.getY(), i.getY()), Math.max(max.getZ(), i.getZ()));
            min = new BlockPos(Math.min(min.getX(), i.getX()), Math.min(min.getY(), i.getY()), Math.min(min.getZ(), i.getZ()));
        }

        Cuboid c = new Cuboid(min, max);
        Cuboid n = c.getFace(Cuboid.CuboidDirection.North);
        Cuboid e = c.getFace(Cuboid.CuboidDirection.East);
        Cuboid w = c.getFace(Cuboid.CuboidDirection.West);
        Cuboid s = c.getFace(Cuboid.CuboidDirection.South);
        Cuboid u = c.getFace(Cuboid.CuboidDirection.Up);
        Cuboid d = c.getFace(Cuboid.CuboidDirection.Down);
        boolean[] valids = {
                isValid(n, "North"),
                isValid(e, "East"),
                isValid(w, "West"),
                isValid(s, "South"),
                isValid(u, "Up"),
                isValid(d, "Down")
        };

        return (c.volume() >= 9 && ((
                valids[0] &&
                        valids[1] &&
                        valids[2] &&
                        valids[3]
        ) ||
                (valids[0] &&
                        valids[3] &&
                        valids[4] &&
                        valids[5]) ||
                (valids[1] &&
                        valids[2] &&
                        valids[4] &&
                        valids[5]))) ? c : null;
    }

    private void setIndicatorBlock(Cuboid c, Block type) {
        for (BlockPos i : c.getBlockPositions()) {
            level.setBlockState(i, type.getDefaultState());
        }
    }

    private boolean isValid(Cuboid frame, String a) {
        if (Math.max(frame.getSizeX(), Math.max(frame.getSizeY(), frame.getSizeZ())) != frame.volume()) {
            System.out.println("Invalid size on " + a + " " + frame.getSizeX() + " " + frame.getSizeY() + " " + frame.getSizeZ());
            return false;
        }

        for (BlockPos i : frame.getBlockPositions()) {
            if (!positionsFound.contains(i)) {
                System.out.println("NOT IN POSITIONS " + a);
                return false;
            }

            if (!BlockRegistry.BLOCKS.contains(level.getBlockState(i).getBlock())) {
                System.out.println("NOT FRAME " + a);
                return false;
            }
        }

        return true;
    }

    public void tick(BlockPos p, int max) {
        if (done || max <= 0) {
            return;
        }

        int checked = positionsFound.size();

        for (BlockPos i : check) {
            BlockPos o = new BlockPos(p.getX() + i.getX(), p.getY() + i.getY(), p.getZ() + i.getZ());
            if (!positionsFound.contains(o)) {
                if (BlockRegistry.BLOCKS.contains(level.getBlockState(o).getBlock())) {
                    positionsFound.add(o);
                    tick(o, max - 1);
                }
            }
        }

        if (checked == positionsFound.size()) {
            done = true;
        }
    }
}