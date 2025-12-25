package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.register.TicgBlockTagProvider;
import com.kirisamey.tconguns.tools.data.TicgStationSlotLayoutProvider;
import com.kirisamey.tconguns.tools.data.TicgToolDefinitionDataProvider;
import com.kirisamey.tconguns.tools.data.TicgToolRecipeProvider;
import com.kirisamey.tconguns.tools.data.TicgToolTagProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenHelper {
    @SubscribeEvent
    static void gatherData(final GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var isServer = event.includeServer();
        var isClient = event.includeClient();

        // tags
        var blockTags = new TicgBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(isServer, blockTags);

        // for tools
        generator.addProvider(isServer, new TicgToolDefinitionDataProvider(packOutput));
        generator.addProvider(isServer, new TicgToolRecipeProvider(packOutput));
        generator.addProvider(isServer, new TicgStationSlotLayoutProvider(packOutput));
        generator.addProvider(isServer, new TicgToolTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
    }
}
