package com.kirisamey.tconguns.modifiers.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.register.data.RecipeProviderBase;
import com.kirisamey.tconguns.tools.TicgToolTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.MultilevelModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

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

        IntStream.range(1, 5).forEach(i -> {
            ModifierRecipeBuilder.modifier(TicgModifiers.EXTENDED_BARREL)
                    .setTools(TicgToolTags.GUN)
                    .exactLevel(i)
                    .setSlots(SlotType.UPGRADE, 1)
                    .addInput(Items.END_ROD, 1)
                    .addInput(switch (i) {
                        case 1 -> Ingredient.of(Items.REDSTONE_TORCH);
                        case 2 -> Ingredient.of(Items.LEVER);
                        case 3 -> Ingredient.of(Items.TRIPWIRE_HOOK);
                        case 4 -> Ingredient.of(Items.LIGHTNING_ROD);
                        default -> throw new IllegalStateException("Unexpected value: " + i);
                    })
                    .addInput(switch (i) {
                        case 1 -> Ingredient.merge(List.of(
                                Ingredient.of(Items.CHICKEN),
                                Ingredient.of(Items.COOKED_CHICKEN),
                                Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "rawchicken"))),
                                Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "raw_chicken"))),
                                Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "cookedchicken"))),
                                Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "cooked_chicken")))
                        ));
                        case 2 -> Ingredient.of(Items.MILK_BUCKET);
                        case 3 -> Ingredient.of(Tags.Items.MUSHROOMS);
                        case 4 -> Ingredient.of(Items.TURTLE_HELMET);
                        default -> throw new IllegalStateException("Unexpected value: " + i);
                    })
                    .saveSalvage(consumer, prefix(TicgModifiers.EXTENDED_BARREL.getId().withSuffix("_" + i), upgradeSalvage))
                    .save(consumer, prefix(TicgModifiers.EXTENDED_BARREL.getId().withSuffix("_" + i), upgradeFolder));
        });

        IntStream.range(1, 5).forEach(i -> {
            var builder = ModifierRecipeBuilder.modifier(TicgModifiers.SHORTENED_BARREL)
                    .setTools(TicgToolTags.GUN)
                    .exactLevel(i)
                    .setSlots(SlotType.UPGRADE, 1)
                    .addInput(Items.END_ROD, 1)
                    .addInput(switch (i) {
                        case 1 -> Ingredient.merge(List.of(
                                Ingredient.of(Tags.Items.SHEARS),
                                Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "tools/knives")))
                        ));
                        case 2 -> Ingredient.of(ItemTags.SWORDS);
                        case 3 -> Ingredient.of(ItemTags.AXES);
                        case 4 -> Ingredient.of(Items.STICKY_PISTON);
                        default -> throw new IllegalStateException("Unexpected value: " + i);
                    });
            if (i == 4) {
                builder.addInput(ItemTags.DOORS)
                        .addInput(ItemTags.BEDS);
            }
            builder.saveSalvage(consumer, prefix(TicgModifiers.SHORTENED_BARREL.getId().withSuffix("_" + i), upgradeSalvage))
                    .save(consumer, prefix(TicgModifiers.SHORTENED_BARREL.getId().withSuffix("_" + i), upgradeFolder));
        });

        IntStream.range(1, 5).forEach(i -> {
            var builder = ModifierRecipeBuilder.modifier(TicgModifiers.EXTENDED_MAGAZINE.getId())
                    .setTools(TicgToolTags.GUN)
                    .exactLevel(i)
                    .setSlots(SlotType.UPGRADE, 1)
                    .addInput(Items.HOPPER, 1);
            if (i % 2 == 1) builder.addInput(Tags.Items.CHESTS, 1);
            else builder.addInput(Items.SHULKER_BOX, 1);
            if (i > 2) builder.addInput(Tags.Items.EGGS, 1);
            builder.saveSalvage(consumer, prefix(TicgModifiers.EXTENDED_MAGAZINE.getId().withSuffix("_" + i), upgradeSalvage))
                    .save(consumer, prefix(TicgModifiers.EXTENDED_MAGAZINE.getId().withSuffix("_" + i), upgradeFolder));
        });
    }

    @Override public @NotNull String getName() {
        return "Ticg Modifier Recipes Provider";
    }
}
