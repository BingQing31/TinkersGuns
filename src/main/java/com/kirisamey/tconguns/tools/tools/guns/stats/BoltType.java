package com.kirisamey.tconguns.tools.tools.guns.stats;

import com.kirisamey.tconguns.tools.tools.guns.GunInputType;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunStats;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunTempStats;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;

import java.util.logging.Level;

@RequiredArgsConstructor
public abstract class BoltType {
    @Getter private final ResourceLocation id;
    @Getter private final TextColor color;

    public Component getDisplay() {
        var key = Util.makeTranslationKey("bolt_type", id);
        return Component.translatable(key).withStyle(style -> style.withColor(color));
    }

    abstract public void handleFire(
            @NotNull LivingEntity user, InteractionHand hand, ServerLevel level, long currentTick,
            @NotNull ItemStack gun, @NotNull IToolStackView gunTool, GunTool gunType, GunInputType inputType,
            GunStats gunStats, GunTempStats tempStats,
            ItemStack ammo, @NotNull ToolStack ammoTool,
            Runnable shot);
}
