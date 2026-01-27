package com.kirisamey.tconguns.toolparts.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.data.model.MaterialModelBuilder;
import slimeknights.tconstruct.library.tools.part.MaterialItem;

import static com.kirisamey.tconguns.toolparts.TicgToolParts.*;

public class TicgToolPartItemModelProvider extends ItemModelProvider {
    public TicgToolPartItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TconGuns.MODID, existingFileHelper);
    }

    @Override protected void registerModels() {
        part(BARREL);
        part(BOLT);
        part(GUN_HANDLE);
        part(MAGAZINE);
        part(GUNBODY_SMALL);

        part(BASE_BULLET_HEAD, "parts/base_bullet_head").offset(-2, 2);
        part(BASE_BULLET_SHELL, "bullet/shell").offset(1, -1);
        part(GUNPOWDER, "parts/gunpowder");
    }

    private MaterialModelBuilder<ItemModelBuilder> part(ResourceLocation part, String texture) {
        return withExistingParent(part.getPath(), "forge:item/default")
                .texture("texture", ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "item/tool/" + texture))
                .customLoader(MaterialModelBuilder::new);
    }

    private MaterialModelBuilder<ItemModelBuilder> part(ItemObject<? extends MaterialItem> part, String texture) {
        return part(part.getId(), texture);
    }

    private MaterialModelBuilder<ItemModelBuilder> part(ItemObject<? extends MaterialItem> part) {
        var id = part.getId();
        return part(id, "parts/" + id.getPath());
    }
}
