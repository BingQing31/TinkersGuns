package com.kirisamey.tconguns.modifiers.impl.guns;

import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.tools.TicgToolStats;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

public class ExtendedMagazineModifier extends Modifier implements ToolStatsModifierHook {
    @Override
    public void addToolStats(@NonNull IToolContext iToolContext, ModifierEntry modifierEntry, @NonNull ModifierStatsBuilder modifierStatsBuilder) {
        var modLevel = modifierEntry.getLevel();
        TicgToolStats.GUN_MAGAZINE_CAPACITY.add(modifierStatsBuilder, 0.5 * modLevel);
    }

    @Override protected void registerHooks(ModuleHookMap.@NonNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
    }
}
