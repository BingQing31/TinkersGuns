package com.kirisamey.tconguns.modifiers.impl.guns;

import com.kirisamey.tconguns.tools.TicgBoltTypes;
import com.kirisamey.tconguns.tools.TicgToolStats;
import org.jspecify.annotations.NonNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;


public class FullAutoModifier extends NoLevelsModifier implements ToolStatsModifierHook {

    @Override
    public void addToolStats(@NonNull IToolContext iToolContext, @NonNull ModifierEntry modifierEntry, @NonNull ModifierStatsBuilder modifierStatsBuilder) {
        TicgToolStats.GUN_BOLT_TYPE.update(modifierStatsBuilder, TicgBoltTypes.FULL_AUTO.get());
    }

    @Override protected void registerHooks(ModuleHookMap.@NonNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
    }

}
