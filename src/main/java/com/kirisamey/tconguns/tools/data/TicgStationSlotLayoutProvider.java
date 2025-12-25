package com.kirisamey.tconguns.tools.data;

import com.kirisamey.tconguns.tools.TicgTools;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.TinkerToolParts;

public class TicgStationSlotLayoutProvider extends AbstractStationSlotLayoutProvider {
    public TicgStationSlotLayoutProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override protected void addLayouts() {
        // bullet
        defineModifiable(TicgTools.BASE_BULLET)
                .sortIndex(SORT_HARVEST)
                .addInputItem(TinkerToolParts.pickHead, 53, 22)
                .addInputItem(TinkerToolParts.toughHandle, 15, 60)
                .addInputItem(TinkerToolParts.toolBinding, 33, 42)
                .build();
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Station Slot Layout Generator";
    }
}
