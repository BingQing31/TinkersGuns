package com.kirisamey.tconguns.register.data;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TicgBlockTagProvider extends BlockTagsProvider {
    public TicgBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TconGuns.MODID, existingFileHelper);
    }

    @Override protected void addTags(@NotNull HolderLookup.Provider provider) {

    }
}
