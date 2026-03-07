package com.kirisamey.tconguns.toolparts.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.toolparts.TicgToolPartCasts;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.registration.CastItemObject;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TicgToolPartCastTagProvider extends ItemTagsProvider {

    public TicgToolPartCastTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                                       CompletableFuture<TagLookup<Block>> pBlockTags,
                                       @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, TconGuns.MODID, existingFileHelper);
    }

    @Override protected void addTags(@NotNull HolderLookup.Provider pProvider) {

        var goldCasts = this.tag(TinkerTags.Items.GOLD_CASTS);
        var sandCasts = this.tag(TinkerTags.Items.SAND_CASTS);
        var redSandCasts = this.tag(TinkerTags.Items.RED_SAND_CASTS);
        var singleUseCasts = this.tag(TinkerTags.Items.SINGLE_USE_CASTS);
        var multiUseCasts = this.tag(TinkerTags.Items.MULTI_USE_CASTS);

        Consumer<CastItemObject> addCast = cast -> {
            // tag based on material
            goldCasts.add(cast.get());
            sandCasts.add(cast.getSand());
            redSandCasts.add(cast.getRedSand());
            // tag based on usage
            singleUseCasts.addTag(cast.getSingleUseTag());
            this.tag(cast.getSingleUseTag()).add(cast.getSand(), cast.getRedSand());
            multiUseCasts.addTag(cast.getMultiUseTag());
            this.tag(cast.getMultiUseTag()).add(cast.get());
        };

        TicgToolPartCasts.FULL_LIST.forEach(addCast);
    }
}
