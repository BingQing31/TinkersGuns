package com.kirisamey.tconguns.frame.items;

import com.kirisamey.tconguns.frame.client.rendering.AnimatableTicTool3DClientItemExtensions;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import java.util.function.Consumer;

public class AnimatableTicTool3DItem extends ModifiableItem {
    public AnimatableTicTool3DItem(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new AnimatableTicTool3DClientItemExtensions());
    }
}
