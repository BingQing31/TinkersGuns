package com.kirisamey.tconguns.tools.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.register.data.RecipeProviderBase;
import com.kirisamey.tconguns.tools.TicgTools;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;

import java.util.function.Consumer;

public class TicgToolRecipeProvider extends RecipeProviderBase implements IToolRecipeHelper {
    public TicgToolRecipeProvider(PackOutput generator) {
        super(generator, TconGuns.MODID);
    }

    @Override protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        String folder = "tools/building/";

        toolBuilding(consumer, TicgTools.BASE_BULLET, folder);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Tool Recipe Generator";
    }

    @Override public @NotNull String getModId() {
        return TconGuns.MODID;
    }
}
