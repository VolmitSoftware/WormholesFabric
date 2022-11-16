package com.volmit.wormholes;

import com.mojang.logging.LogUtils;
import com.volmit.wormholes.content.BlockRegistry;
import com.volmit.wormholes.content.ItemRegistry;
import com.volmit.wormholes.content.VoidChunkGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class Wormholes implements ModInitializer {

    public static final String MOD_ID = "wormholes";
    public static final RegistryKey<World> VOID_DIM = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "void"));
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> LOGGER.info("""  
                          
                ██╗    ██╗ ██████╗ ██████╗ ███╗   ███╗██╗  ██╗ ██████╗ ██╗     ███████╗███████╗
                ██║    ██║██╔═══██╗██╔══██╗████╗ ████║██║  ██║██╔═══██╗██║     ██╔════╝██╔════╝
                ██║ █╗ ██║██║   ██║██████╔╝██╔████╔██║███████║██║   ██║██║     █████╗  ███████╗
                ██║███╗██║██║   ██║██╔══██╗██║╚██╔╝██║██╔══██║██║   ██║██║     ██╔══╝  ╚════██║
                ╚███╔███╔╝╚██████╔╝██║  ██║██║ ╚═╝ ██║██║  ██║╚██████╔╝███████╗███████╗███████║
                ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝                                
                        By: Volmit Software (Arcane Arts)
                """));
        ItemRegistry.registerItems();
        BlockRegistry.registerBlocks();
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "void"), VoidChunkGenerator.CODEC);

    }
}