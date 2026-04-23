package com.kirisamey.tconguns.sounds.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.sounds.TicgSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.Objects;

public class TicgSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public TicgSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, TconGuns.MODID, helper);
    }

    @Override public void registerSounds() {
        TicgSounds.FullList.forEach(t -> t.apply((regObj, defInfo, le) -> {
            var idPath = Objects.requireNonNull(regObj.getId()).getPath();
            var subtitle = "subtitles.%s.%s".formatted(TconGuns.MODID, idPath);

            this.add(regObj, definition()
                    .subtitle(subtitle)
                    .with(sound(ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, defInfo.fileName()))
                            .volume(defInfo.volume())
                            .pitch(defInfo.pitch())
                            .stream(defInfo.stream())
                    )
            );
            return 0;
        }));
    }
}
