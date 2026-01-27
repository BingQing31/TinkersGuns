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
        // guns
        defineModifiable(TicgTools.GUN_SMALL)
                .sortIndex(SORT_RANGED)
                .addInputItem(TicgToolParts.GUNBODY_SMALL, 28, 30)
                .addInputItem(TicgToolParts.BARREL, 48, 30)
                .addInputItem(TicgToolParts.BOLT, 34, 50)
                .addInputItem(TicgToolParts.MAGAZINE, 54, 54)
                .addInputItem(TicgToolParts.GUN_HANDLE, 14,50)
                .build();


        // bullet
        defineModifiable(TicgTools.BASE_BULLET)
                .sortIndex(SORT_RANGED)
                .addInputItem(TicgToolParts.BASE_BULLET_HEAD, 44, 30)
                .addInputItem(TicgToolParts.BASE_BULLET_SHELL, 26, 48)
                .addInputItem(TicgToolParts.GUNPOWDER, 52, 56)
                .build();
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Station Slot Layout Generator";
    }
}
