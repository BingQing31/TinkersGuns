package com.kirisamey.tconguns.toolparts.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.register.data.RecipeProviderBase;
import com.kirisamey.tconguns.toolparts.TicgToolPartCasts;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

import java.util.function.Consumer;

public class TicgToolPartRecipeProvider extends RecipeProviderBase implements IToolRecipeHelper {
    public TicgToolPartRecipeProvider(PackOutput generator) {
        super(generator, TconGuns.MODID);
    }

    private static final String PART_FOLDER = "tools/parts/";
    private static final String CAST_FOLDER = "smeltery/casts/";

    @Override protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        part(consumer, TicgToolParts.BASE_BULLET_HEAD, TicgToolPartCasts.BASE_BULLET_HEAD, 1);
        part(consumer, TicgToolParts.BASE_BULLET_SHELL, TicgToolPartCasts.BASE_BULLET_SHELL, 1);
        part(consumer, TicgToolParts.GUNPOWDER, TicgToolPartCasts.GUNPOWDER, 2);

        part(consumer, TicgToolParts.BARREL, TicgToolPartCasts.BARREL, 2);
        part(consumer, TicgToolParts.BOLT, TicgToolPartCasts.BOLT, 1);
        part(consumer, TicgToolParts.GUN_HANDLE, TicgToolPartCasts.GUN_HANDLE, 2);
        part(consumer, TicgToolParts.MAGAZINE, TicgToolPartCasts.MAGAZINE, 2);
        part(consumer, TicgToolParts.GUNBODY_SMALL, TicgToolPartCasts.GUNBODY_SMALL, 5);
    }

    private void part(Consumer<FinishedRecipe> consumer, ItemObject<ToolPartItem> part, CastItemObject cast, int cost) {
        partRecipes(consumer, part, cast, cost, PART_FOLDER, CAST_FOLDER);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Tool Part Recipe Generator";
    }
}
