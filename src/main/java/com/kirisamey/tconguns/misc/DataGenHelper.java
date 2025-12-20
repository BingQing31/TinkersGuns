package com.kirisamey.tconguns.misc;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.data.TicgToolDefinitionDataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenHelper {
    @SubscribeEvent
    static void gatherData(final GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        // var existingFileHelper = event.getExistingFileHelper();
        var isServer = event.includeServer();
        // var isClient = event.includeClient();
        generator.addProvider(isServer, new TicgToolDefinitionDataProvider(packOutput));
    }
}
