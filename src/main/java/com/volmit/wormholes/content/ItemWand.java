package com.volmit.wormholes.content;

import com.volmit.wormholes.utils.Cuboid;
import com.volmit.wormholes.utils.Framer;
import com.volmit.wormholes.utils.PortalUtil;
import com.volmit.wormholes.utils.SoundUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import qouteall.imm_ptl.core.portal.Portal;

import java.util.*;

public class ItemWand extends Item {
    public ItemWand(Settings settings) {
        super(settings.maxCount(1).fireproof()
                .maxDamage(86).rarity(Rarity.RARE));
    }

    public void clear(ItemStack item) {
        item.removeSubNbt("wormholesdim");
        item.removeSubNbt("wormholesframe");
        item.removeSubNbt("wormholesdir");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            return super.use(world, user, hand);
        }
        if (user.isSneaking() && getNearestEntity(user).getEntity() instanceof Portal p) {

            if (p.getDestDim().toString().contains("wormholes") || p.getOriginDim().toString().contains("wormholes")) {
                SoundUtil.play((ServerWorld) user.getWorld(), user.getPos(), SoundEvents.BLOCK_ENDER_CHEST_OPEN, 1f, 3.25f);
                return super.use(world, user, hand);
            }

            if (user.getServer() != null && user.getWorld().getServer() != null) {

                System.out.println("Rotating portal: " + user.getYaw() / 100);
                double yaw = user.getYaw();
                double pitch = user.getPitch();
//                System.out.println("Yaw: " + yaw + " Pitch: " + pitch);
                if (pitch > 50 || pitch < -50) {
                    System.out.println("Rotating portal on the X axis");
                    Objects.requireNonNull(world.getServer()).getCommandManager()
                            .execute(new ServerCommandSource(user, user.getEyePos(), user.getRotationClient(), (ServerWorld) user.getWorld(), 4, "Wormhole", user.getDisplayName(), user.getWorld().getServer(), user), "/portal rotate_portal_rotation_along x 180");
                    SoundUtil.play((ServerWorld) user.getWorld(), user.getPos(), SoundEvents.BLOCK_CHEST_CLOSE, 1f, 3.25f);
                } else {
                    System.out.println("Rotating portal on the Z axis");
                    Objects.requireNonNull(world.getServer()).getCommandManager().execute(new ServerCommandSource(user, user.getEyePos(), user.getRotationClient(), (ServerWorld) user.getWorld(), 4, "Wormhole", user.getDisplayName(), user.getWorld().getServer(), user), "/portal rotate_portal_rotation_along y 180");
                    SoundUtil.play((ServerWorld) user.getWorld(), user.getPos(), SoundEvents.BLOCK_CHEST_CLOSE, 1f, 3.25f);
                }
            }

        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) {
            return super.useOnBlock(context);
        }

        if (context.getPlayer() == null) {
            return super.useOnBlock(context);
        }

        if (getNearestEntity(context.getPlayer()).getEntity() instanceof Portal) {
            return super.useOnBlock(context);
        }

        context.getPlayer().getItemCooldownManager().set(this, 5);
        if (context.getPlayer().isSneaking()) {
            context.getPlayer().sendMessage(Text.of("Hole Applicator Cleared."), true);
            clear(context.getStack());
            SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 1f, 0.5f);
            SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_DEEPSLATE_BREAK, 1f, 0.8f);
            return super.useOnBlock(context);
        }

        BlockPos pos = context.getBlockPos();

        if (BlockRegistry.BLOCKS.contains(context.getWorld().getBlockState(pos).getBlock())) {
            Framer f = new Framer(context.getWorld(), pos);
            Cuboid c = f.validate();
            BlockPos playerPos = context.getPlayer().getBlockPos();

            if (c != null) {
                if (hasData(context.getStack())) {
                    if (PortalUtil.linkPortals(context.getPlayer(), (ServerWorld) context.getWorld(), getDirection(context.getStack()), getDimension(context.getStack()),
                            getCuboid(context.getStack()), computeDirection(playerPos, c.getCenter(), c), context.getWorld().getRegistryKey().getValue().toString(), c.clone())) {
                        context.getPlayer().sendMessage(Text.of("Gateway Created!"), true);

                        clear(context.getStack());
                        if (!context.getPlayer().isCreative()) {
                            context.getStack().setDamage(context.getStack().getDamage() + 1);
                        }
                        SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_BELL_RESONATE, 1f, 0.9f);
                        SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_BELL_RESONATE, 1f, 1.25f);
                        SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_END_PORTAL_SPAWN, 0.25f, 0.5f);
                    } else {
                        clear(context.getStack());
                        SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 1f, 0.2f);
                    }
                    return super.useOnBlock(context);
                }

                context.getPlayer().sendMessage(Text.of("First Portal Frame Bound!"), true);
                setCuboid(context.getStack(), c);
                setDimension(context.getStack(), context.getWorld().getRegistryKey().getValue().toString());
                setDirection(context.getStack(), computeDirection(playerPos, c.getCenter(), c));
                SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_DEEPSLATE_TILES_BREAK, 1f, 1.85f);
                SoundUtil.play((ServerWorld) context.getWorld(), context.getPlayer().getPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_PLACE, 1f, 0.5f);
            }
        }
        return super.useOnBlock(context);
    }

    public boolean hasData(ItemStack stack) {
        return stack.hasNbt() && getCuboid(stack) != null && getDimension(stack) != null && getDirection(stack) != null;
    }

    public void setDimension(ItemStack stack, String dim) {
        stack.getOrCreateNbt().putString("wormholesdim", dim);
    }

    public String getDimension(ItemStack stack) {
        return stack.getOrCreateNbt().getString("wormholesdim");
    }

    public static Direction computeDirection(BlockPos point, BlockPos toward, Cuboid cc) {
        Set<Direction> allowed = new HashSet<>();

        if (cc == null) {
            allowed.addAll(Arrays.stream(Direction.values()).toList());
        } else if (cc.getSizeY() == 1) {
            allowed.add(Direction.UP);
            allowed.add(Direction.DOWN);
        } else if (cc.getSizeX() == 1) {
            allowed.add(Direction.EAST);
            allowed.add(Direction.WEST);
        } else if (cc.getSizeZ() == 1) {
            allowed.add(Direction.NORTH);
            allowed.add(Direction.SOUTH);
        }

        BlockPos vec = toward.subtract(point);
        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();
        double l = Math.sqrt(x * x + y * y + z * z);
        x /= l;
        y /= l;
        z /= l;

        Vec3d v = new Vec3d(x, y, z);

        double m = Double.MAX_VALUE;
        Direction d = Direction.NORTH;

        for (Direction i : allowed) {
            double dx = v.distanceTo(new Vec3d(i.getOffsetX(), i.getOffsetY(), i.getOffsetZ()));

            if (dx < m) {
                m = dx;
                d = i;
            }
        }

        return d;
    }

    public Direction getDirection(ItemStack stack) {
        return Direction.byId(stack.getOrCreateNbt().getInt("wormholesdir"));
    }

    public void setDirection(ItemStack stack, Direction dir) {
        stack.getOrCreateNbt().putInt("wormholesdir", dir.getId());
    }

    public void setCuboid(ItemStack stack, Cuboid c) {
        stack.getOrCreateNbt().putIntArray("wormholesframe", new int[]{
                c.getLowerX(),
                c.getLowerY(),
                c.getLowerZ(),
                c.getUpperX(),
                c.getUpperY(),
                c.getUpperZ(),
        });
    }

    @NotNull
    public static EntityHitResult getNearestEntity(PlayerEntity player) {
        float playerRotX = player.getRotationClient().x;
        float playerRotY = player.getRotationClient().y;
        Vec3d startPos = player.getEyePos();
        float f2 = (float) Math.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = (float) Math.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = (float) -Math.cos(-playerRotX * ((float) Math.PI / 180F));
        float additionY = (float) Math.sin(-playerRotX * ((float) Math.PI / 180F));
        float additionX = f3 * f4;
        float additionZ = f2 * f4;
        double d0 = 15;
        Vec3d endVec = startPos.add((double) additionX * d0, (double) additionY * d0, (double) additionZ * d0);
        Box startEndBox = new Box(startPos, endVec);
        Entity entity = player;
        for (Entity entity1 : player.world.getOtherEntities(player, startEndBox, (val) -> true)) {
            Box box = entity1.getBoundingBox().expand(entity1.getTargetingMargin());
            Optional<Vec3d> optional = box.raycast(startPos, endVec);
            if (box.contains(startPos)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    startPos = optional.orElse(startPos);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3d Vec3d1 = optional.get();
                double d1 = startPos.squaredDistanceTo(Vec3d1);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getRootVehicle() == player.getRootVehicle() && !entity1.hasPlayerRider()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            startPos = Vec3d1;
                        }
                    } else {
                        entity = entity1;
                        startPos = Vec3d1;
                        d0 = d1;
                    }
                }
            }
        }
        if (entity == player) {
            return new EntityHitResult(player);
        } else {
            return new EntityHitResult(entity);
        }
    }

    public Cuboid getCuboid(ItemStack item) {
        try {
            assert item.getNbt() != null;
            int[] f = item.getNbt().getIntArray("wormholesframe");
            return new Cuboid(f[0], f[1], f[2], f[3], f[4], f[5]);
        } catch (Throwable e) {
            return null;
        }
    }
}