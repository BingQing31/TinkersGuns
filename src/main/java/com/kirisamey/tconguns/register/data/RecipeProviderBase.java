package com.kirisamey.tconguns.register.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.data.IRecipeHelper;

import java.util.function.Consumer;

public abstract class RecipeProviderBase extends RecipeProvider implements IConditionBuilder, IRecipeHelper {
    public RecipeProviderBase(PackOutput generator, String modId) {
        super(generator);
        this.modId = modId;
    }

    private final String modId;

    protected abstract void buildRecipes(Consumer<FinishedRecipe> consumer);

    @Override public abstract @NotNull String getName();

    @Override public @NotNull String getModId() {
        return modId;
    }

}
