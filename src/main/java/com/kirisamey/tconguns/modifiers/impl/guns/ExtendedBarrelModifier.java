package com.kirisamey.tconguns.modifiers.impl.guns;

import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.tools.TicgToolStats;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import org.checkerframework.checker.units.qual.C;
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

public class ExtendedBarrelModifier extends Modifier implements ToolStatsModifierHook, ValidateModifierHook {

    public static ExtendedBarrelModifier extend() {
        return new ExtendedBarrelModifier();
    }

    public static ExtendedBarrelModifier shorten() {
        var result = new ExtendedBarrelModifier();
        result.extend = false;
        return result;
    }

    @Getter private boolean extend = true;

    @Override
    public void addToolStats(@NonNull IToolContext iToolContext, ModifierEntry modifierEntry, @NonNull ModifierStatsBuilder modifierStatsBuilder) {
        var modLevel = modifierEntry.getLevel();
        TicgToolStats.GUN_ACCURACY.add(modifierStatsBuilder, 0.05 * modLevel * (extend ? 1 : -1));
        TicgToolStats.GUN_VELOCITY.add(modifierStatsBuilder, 0.03 * modLevel * (extend ? 1 : -1));
    }

    @Override public @Nullable Component validate(@NonNull IToolStackView iToolStackView, @NonNull ModifierEntry modifierEntry) {
        if(iToolStackView.getModifier((extend ? TicgModifiers.SHORTENED_BARREL : TicgModifiers.EXTENDED_BARREL).getId()) != ModifierEntry.EMPTY)
            return Component.translatable("modifier.tconguns.extended_barrel.confliction");
        return null;
    }

    @Override protected void registerHooks(ModuleHookMap.@NonNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
        hookBuilder.addHook(this, ModifierHooks.VALIDATE);
    }
}
