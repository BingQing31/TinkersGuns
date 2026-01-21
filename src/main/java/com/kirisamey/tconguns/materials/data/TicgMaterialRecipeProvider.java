package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.materials.TicgMaterialIds;
import com.kirisamey.tconguns.register.data.RecipeProviderBase;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;

import java.util.function.Consumer;

public class TicgMaterialRecipeProvider extends RecipeProviderBase implements IMaterialRecipeHelper {

    public TicgMaterialRecipeProvider(PackOutput generator) {
        super(generator, TconGuns.MODID);
    }

    @Override protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        addMaterialItems(consumer);
        addMaterialSmeltery(consumer);
    }

    private void addMaterialItems(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        materialRecipe(consumer, TicgMaterialIds.GUNPOWDER,
                Ingredient.of(Tags.Items.GUNPOWDER), 1, 1, folder + "gunpowder");
        materialRecipe(consumer, TicgMaterialIds.REDSTONE,
                Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1, 1, folder + "redstone/dusts");
        materialRecipe(consumer, TicgMaterialIds.REDSTONE,
                Ingredient.of(Tags.Items.STORAGE_BLOCKS_REDSTONE), 9, 1, folder + "redstone/blocks");
        materialRecipe(consumer, TicgMaterialIds.BLAZE_POWDER,
                Ingredient.of(Items.BLAZE_POWDER), 1, 1, folder + "blaze_powder");
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {

    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Recipe Provider";
    }
}
