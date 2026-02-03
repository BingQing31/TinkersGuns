package com.kirisamey.tconguns.tools.impl;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import lombok.extern.log4j.Log4j2;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

@Log4j2
public abstract class GunTool extends ModifiableItem {
    public GunTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }


    @Override
    public @NotNull List<Component> getStatInformation(
            @NotNull IToolStackView tool, @Nullable Player player, @NotNull List<Component> tooltips,
            @NotNull TooltipKey key, @NotNull TooltipFlag tooltipFlag) {
        tooltips = super.getStatInformation(tool, player, tooltips, key, tooltipFlag);
        tooltips.add(ToolStatShowUtils.percentStatFormat(tool, TicgToolStats.GUN_ATTACK));
        tooltips.add(ToolStatShowUtils.percentStatFormat(tool, TicgToolStats.GUN_VELOCITY));
        tooltips.add(ToolStatShowUtils.reversedPercentStatFormat(tool, TicgToolStats.GUN_RECOIL));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_SHOT_SPEED));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_ACCURACY));
        tooltips.add(TicgToolStats.GUN_MAGAZINE_CAPACITY.formatValue(
                        (float) Math.floor(tool.getStats()
                                .get(TicgToolStats.GUN_MAGAZINE_CAPACITY))
                )
        );
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_RELOAD_SPEED));
        return tooltips;
    }


    @Override public boolean canAttackBlock(@NotNull BlockState block, @NotNull Level level,
                                            @NotNull BlockPos pos, @NotNull Player player) {
        return false;
    }


    public void entityFire(@NotNull LivingEntity user, @NotNull IToolStackView gunTool, boolean firstPress) {
        // todo: implement
    }
}
