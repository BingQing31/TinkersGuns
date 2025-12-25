package com.kirisamey.tconguns.tools.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgTools;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.IdAwareObject;

import java.util.concurrent.CompletableFuture;

import static slimeknights.tconstruct.common.TinkerTags.Items.*;

public class TicgToolTagProvider extends ItemTagsProvider {
    public TicgToolTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTagProvider,
                               ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, TconGuns.MODID, existingFileHelper);
    }

    @Override protected void addTags(@NotNull HolderLookup.Provider provider) {
        //todo: 定义自己的tags
        addToolTags(TicgTools.BASE_BULLET, MULTIPART_TOOL, DURABILITY, SMALL_TOOLS);
    }

    @SafeVarargs
    private void addToolTags(ItemLike tool, TagKey<Item>... tags) {
        Item item = tool.asItem();
        for (TagKey<Item> tag : tags) {
            this.tag(tag).add(item);
        }
    }

    @SafeVarargs
    private void optionalToolTags(IdAwareObject tool, TagKey<Item>... tags) {
        ResourceLocation id = tool.getId();
        for (TagKey<Item> tag : tags) {
            this.tag(tag).addOptional(id);
        }
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Tool Tags Generator";
    }

}
