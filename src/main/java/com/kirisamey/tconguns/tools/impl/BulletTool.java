package com.kirisamey.tconguns.tools.impl;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class BulletTool extends ModifiableItem {
    public BulletTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override
    public @NotNull List<Component> getStatInformation(
            @NotNull IToolStackView tool, @Nullable Player player, @NotNull List<Component> tooltips,
            @NotNull TooltipKey key, @NotNull TooltipFlag tooltipFlag) {
        tooltips = super.getStatInformation(tool, player, tooltips, key, tooltipFlag);
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.BULLET_ATTACK));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.BULLET_VELOCITY));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.BULLET_VELOCITY_ATTENUATION));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.BULLET_RECOIL));
//        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.BULLET_VOLUME));
        return tooltips;
    }
}
