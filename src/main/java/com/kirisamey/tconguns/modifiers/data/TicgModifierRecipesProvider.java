package com.kirisamey.tconguns.modifiers.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.register.data.RecipeProviderBase;
import com.kirisamey.tconguns.tools.TicgToolTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.function.Consumer;

public class TicgModifierRecipesProvider extends RecipeProviderBase {
    public TicgModifierRecipesProvider(PackOutput generator) {
        super(generator, TconGuns.MODID);
    }

    @Override protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // modifier folders
        String upgradeFolder = "tools/modifiers/upgrade/";
        String abilityFolder = "tools/modifiers/ability/";
        String slotlessFolder = "tools/modifiers/slotless/";
        String defenseFolder = "tools/modifiers/defense/";
        String compatFolder = "tools/modifiers/compat/";
        String worktableFolder = "tools/modifiers/worktable/";
        // salvage
        String salvageFolder = "tools/modifiers/salvage/";
        String upgradeSalvage = salvageFolder + "upgrade/";
        String abilitySalvage = salvageFolder + "ability/";
        String defenseSalvage = salvageFolder + "defense/";
        String compatSalvage = salvageFolder + "compat/";

        // guns
        ModifierRecipeBuilder.modifier(TicgModifiers.FULL_AUTO)
                .setTools(TicgToolTags.GUN)
                .setMaxLevel(1)
                .setSlots(SlotType.ABILITY, 1)
                .addInput(Items.REDSTONE, 3)
                .addInput(Items.LEVER)
                .addInput(Items.COMPARATOR)
                .saveSalvage(consumer, prefix(TicgModifiers.FULL_AUTO, abilitySalvage))
                .save(consumer, prefix(TicgModifiers.FULL_AUTO, abilityFolder));

        ModifierRecipeBuilder.modifier(TicgModifiers.SIGHT_HOLO)
                .setTools(TicgToolTags.GUN)
                .setMaxLevel(1)
                .addInput(Items.END_ROD, 13)
                .save(consumer, prefix(TicgModifiers.SIGHT_HOLO, slotlessFolder));
    }

    @Override public @NotNull String getName() {
        return "";
    }
}
