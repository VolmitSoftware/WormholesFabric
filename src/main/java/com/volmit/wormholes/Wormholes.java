package com.volmit.wormholes;

import com.mojang.logging.LogUtils;
import com.volmit.wormholes.content.BlockRegistry;
import com.volmit.wormholes.content.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;

public class Wormholes implements ModInitializer {

    public static final String MOD_ID = "wormholes";
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
    }
}