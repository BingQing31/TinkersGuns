package com.kirisamey.tconguns.tools.data;

import com.kirisamey.tconguns.toolparts.TicgToolParts;
import com.kirisamey.tconguns.tools.TicgTools;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;

public class TicgStationSlotLayoutProvider extends AbstractStationSlotLayoutProvider {
    public TicgStationSlotLayoutProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override protected void addLayouts() {
        // bullet
        defineModifiable(TicgTools.BASE_BULLET)
                .sortIndex(SORT_HARVEST)
                .addInputItem(TicgToolParts.BASE_BULLET_HEAD, 44, 30)
                .addInputItem(TicgToolParts.BASE_BULLET_SHELL, 26, 48)
                .addInputItem(TicgToolParts.GUNPOWDER, 52, 56)
                .build();
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Station Slot Layout Generator";
    }
}
