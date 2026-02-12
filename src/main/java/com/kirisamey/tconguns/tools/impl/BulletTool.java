package com.kirisamey.tconguns.tools.impl;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;
import java.util.logging.Level;

@Log4j2
public abstract class BulletTool extends ModifiableItem {
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

    public void drawHitParticle(ClientLevel level, Vec3 pos, Vec3 velocity) {
        var spd = velocity.normalize().scale(-1);

        for (int i = 0; i < 8; i++) {
            var dr = 0.125;
            // 再说一次 Jvav 就是一坨
            // nextDouble() 返回 0 到他妈 MaxValue
            // nextDouble(min, max) 的最小值不能小于0
            // 并且他们扯的这个强异常检查强在哪？
            // that's why I always say that Java is a piece of sh*t
            // nextDouble() returns 0 to f*king MaxValue
            // nextDouble(min, max) cannot have a min value less than zero
            // and the so-called strong exception checking is not strong at all
            var dy = (RandomUtils.nextDouble(0, 2) - 1) * dr;
            var dz = (RandomUtils.nextDouble(0, 2) - 1) * dr;
            var dx = (RandomUtils.nextDouble(0, 2) - 1) * dr;
            log.debug("dx{} dy{} dz{}", dx, dy, dz);
            level.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, spd.x + dx, spd.y + dy, spd.z + dz);
//            level.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, spd.x, spd.y, spd.z);
        }
    }
}
