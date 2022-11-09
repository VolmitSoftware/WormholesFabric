package com.volmit.wormholes.utils;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class SoundUtil {
    public static void play(ServerWorld level, Vec3d v3, SoundEvent sound, float volume, float pitch) {
        level.playSound(null, v3.x, v3.y, v3.z, sound, SoundCategory.BLOCKS, volume, pitch);
    }
}