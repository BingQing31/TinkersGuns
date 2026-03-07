package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.materials.data.*;
import com.kirisamey.tconguns.register.data.TicgBlockTagProvider;
import com.kirisamey.tconguns.toolparts.data.TicgToolPartCastTagProvider;
import com.kirisamey.tconguns.toolparts.data.TicgToolPartItemModelProvider;
import com.kirisamey.tconguns.toolparts.data.TicgToolPartRecipeProvider;
import com.kirisamey.tconguns.tools.data.TicgStationSlotLayoutProvider;
import com.kirisamey.tconguns.tools.data.TicgToolDefinitionDataProvider;
import com.kirisamey.tconguns.tools.data.TicgToolRecipeProvider;
import com.kirisamey.tconguns.tools.data.TicgToolTagProvider;
import com.kirisamey.tconguns.gui.data.TicgGuiSpriteSourceProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

        // for materials
        var mat = new TicgMaterialDataProvider(packOutput);
        generator.addProvider(isServer, mat);
        generator.addProvider(isServer, new TicgMaterialStatsDataProvider(packOutput, mat));
        generator.addProvider(isServer, new TicgMaterialTraitDataProvider(packOutput, mat));
        generator.addProvider(isServer, new TicgMaterialRecipeProvider(packOutput));

        var materialSprites = new TicgMaterialSpriteProvider();
        generator.addProvider(isClient, new TicgMaterialRenderInfoProvider(packOutput, materialSprites, existingFileHelper));

        // for tool parts & casts
        generator.addProvider(isClient, new TicgToolPartItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(isServer, new TicgToolPartRecipeProvider(packOutput));
        generator.addProvider(isServer, new TicgToolPartCastTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

        // for tools
        generator.addProvider(isServer, new TicgToolDefinitionDataProvider(packOutput));
        generator.addProvider(isServer, new TicgToolRecipeProvider(packOutput));
        generator.addProvider(isServer, new TicgStationSlotLayoutProvider(packOutput));
        generator.addProvider(isServer, new TicgToolTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

        // for gui
        generator.addProvider(isClient, new TicgGuiSpriteSourceProvider(packOutput, existingFileHelper));

        // datapack
        generator.addProvider(isServer, new TicgDataPackProvider(packOutput, lookupProvider));
    }
}
