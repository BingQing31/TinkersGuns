package com.kirisamey.tconguns.sounds;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.register.TicgModuleBase;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class TicgSounds extends TicgModuleBase {

    public static ArrayList<Tuple3<
            RegistryObject<SoundEvent>,
            SoundDefinitionInfo,
            LanguageEntry
            >> FullList = new ArrayList<>();

    public static final RegistryObject<SoundEvent> SHOT_DEFAULT = sound("shot_default",
            new LanguageEntry("Tinkers Gun: Shot", "工匠枪械：射击"),
            "shot_default", 1f, 1f, false
    );


    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<SoundEvent> sound(String id, LanguageEntry languageEntry, String fileName, float volume, float pitch, boolean stream) {
        var rObj = SOUNDS.register(id,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, id)));
        var defInfo = new SoundDefinitionInfo(fileName, volume, pitch, stream);
        FullList.add(Tuple.of(rObj, defInfo, languageEntry));
        return rObj;
    }

    public record SoundDefinitionInfo(String fileName, float volume, float pitch, boolean stream) {
    }
}
