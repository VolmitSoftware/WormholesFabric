package com.volmit.wormholes.utils;

import com.volmit.wormholes.content.BlockRegistry;
import com.volmit.wormholes.content.FrameBlock;
import com.volmit.wormholes.content.ItemWand;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import qouteall.imm_ptl.core.McHelper;
import qouteall.imm_ptl.core.api.PortalAPI;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalExtension;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.my_util.DQuaternion;

import java.util.HashSet;
import java.util.Set;

public class PortalUtil {

    public static boolean linkPortals(PlayerEntity player, ServerWorld world, Direction dir1, String dim1, Cuboid c1, Direction dir2, String dim2, Cuboid c2) {
        Set<BlockPos> positions1 = new HashSet<>();
        Set<BlockPos> positions2 = new HashSet<>();
        ServerWorld l1 = world.getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, new Identifier(dim1)));
        ServerWorld l2 = world.getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, new Identifier(dim2)));

        for (BlockPos i : c1.getBlockPositions()) {
            assert l1 != null;
            if (BlockRegistry.BLOCKS.contains(l1.getBlockState(i).getBlock())) {
                positions1.add(i);
            }
        }

        for (BlockPos i : c2.getBlockPositions()) {
            assert l1 != null;
            if (BlockRegistry.BLOCKS.contains(l1.getBlockState(i).getBlock())) {
                positions2.add(i);
            }
        }

        c1 = c1.insetPortal(l1);
        c2 = c2.insetPortal(l2);
        Box frame1 = fix(c1.box(), dir1, dir2);
        Box frame2 = fix(c2.box(), dir2, dir1);
        Vec3d pos1 = frame1.getCenter();
        Vec3d pos2 = frame2.getCenter();
        Vec3d angle1 = new Vec3d(dir1.getOffsetX(), dir1.getOffsetY(), dir1.getOffsetZ());
        Vec3d angle2 = new Vec3d(dir2.getOffsetX(), dir2.getOffsetY(), dir2.getOffsetZ());
        
        DQuaternion q1 = getQuaternion(angle1, angle2, dir1.equals(dir2.getOpposite()));
        DQuaternion q2 = getQuaternion(angle2, angle1, dir2.equals(dir1.getOpposite()));
        Portal portal = Portal.entityType.create(l1);
        assert portal != null;
        PortalAPI.setPortalOrthodoxShape(portal, dir1, frame1);
        portal.setDestinationDimension(RegistryKey.of(Registry.WORLD_KEY, new Identifier(dim2)));
        portal.setDestination(pos2);
        portal.setPos(pos1.x, pos1.y, pos1.z);
        portal.setRotationTransformationD(q1);

        Portal portal2 = Portal.entityType.create(l2);
        assert portal2 != null;
        PortalAPI.setPortalOrthodoxShape(portal2, dir2, frame2);
        portal2.setDestinationDimension(RegistryKey.of(Registry.WORLD_KEY, new Identifier(dim1)));
        portal2.setDestination(pos1);
        portal2.setPos(pos2.x, pos2.y, pos2.z);
        portal2.setRotationTransformationD(q2);

        PortalExtension.get(portal).bindCluster = true;
        PortalExtension.get(portal2).bindCluster = true;

        McHelper.spawnServerEntity(configure(portal));
        McHelper.spawnServerEntity(configure(portal2));
        McHelper.spawnServerEntity(configure(PortalManipulation.createFlippedPortal(portal, (EntityType<Portal>) portal.getType())));
        McHelper.spawnServerEntity(configure(PortalManipulation.createFlippedPortal(portal2, (EntityType<Portal>) portal2.getType())));

        for (BlockPos i : positions1) {
            if (BlockRegistry.BLOCKS.contains(l1.getBlockState(i).getBlock())) {
                BlockState state = l1.getBlockState(i);
                l1.setBlockState(i, FrameBlock.linkPortal(ItemWand.computeDirection(i, new BlockPos((int) pos1.x, (int) pos1.y, (int) pos1.z), null), state));
            }
        }

        for (BlockPos i : positions2) {
            if (BlockRegistry.BLOCKS.contains(l2.getBlockState(i).getBlock())) {
                BlockState state = l2.getBlockState(i);
                l2.setBlockState(i, FrameBlock.linkPortal(ItemWand.computeDirection(i, new BlockPos((int) pos2.x, (int) pos2.y, (int) pos2.z), null), state));
            }
        }

        return true;
    }

    private static Portal configure(Portal p) {
        return p;
    }

    private static DQuaternion getQuaternion(Vec3d angle1, Vec3d angle2, boolean flip) {
        if (flip) {
            return DQuaternion.identity; //  DQuaternion.rotationByDegrees(angle1, 180);
        }

        Vec3d cross = angle1.crossProduct(angle2);
        return new DQuaternion((float) cross.x, (float) cross.y, (float) cross.z, (float) Math.sqrt(
                (angle1.lengthSquared() * angle2.lengthSquared()) + angle1.dotProduct(angle2)))
                .getNormalized();
    }

    private static Box fix(Box box, Direction direction, Direction otherDirection) {
        if (isPositive(direction)) {
            return box.offset(direction.getOffsetX() * 0.5, direction.getOffsetY() * 0.5, direction.getOffsetZ() * 0.5);
        } else {
            return box.offset(-direction.getOffsetX() * 0.5, -direction.getOffsetY() * 0.5, -direction.getOffsetZ() * 0.5);
        }
    }

    private static boolean isPositive(Direction direction) {
        return Math.max(Math.max(direction.getOffsetX(), direction.getOffsetY()), direction.getOffsetZ()) == 1;
    }

    private static Vec3d clean(Vec3d f) {
        return new Vec3d((int) f.x, (int) f.y, (int) f.z);
    }
}