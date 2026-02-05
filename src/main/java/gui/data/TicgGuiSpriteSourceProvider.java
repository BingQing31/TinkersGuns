package gui.data;

import com.kirisamey.tconguns.TconGuns;
import gui.TicgGuiTextures;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

public class TicgGuiSpriteSourceProvider extends SpriteSourceProvider {
    public TicgGuiSpriteSourceProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper, TconGuns.MODID);
    }

    SourceList blockAtlas = atlas(BLOCKS_ATLAS);

    @Override protected void addSources() {
//        blockAtlas.addSource(new SingleFile(
//                TicgGuiTextures.BULLET_PATTERN,
//                Optional.empty()
//        ));
        blockAtlas.addSource(new DirectoryLister(
                TicgGuiTextures.ITEM_PATTERN_DIR,
                TicgGuiTextures.ITEM_PATTERN_DIR + "/"
        ));
    }
}
