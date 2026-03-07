package com.kirisamey.tconguns.toolparts;

import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class TicgToolPartCasts extends TicgModuleBase {

    public static final List<CastItemObject> FULL_LIST = new ArrayList<>();

    public static final CastItemObject BASE_BULLET_HEAD = cast(TicgToolParts.BASE_BULLET_HEAD);
    public static final CastItemObject BASE_BULLET_SHELL = cast(TicgToolParts.BASE_BULLET_SHELL);
    public static final CastItemObject GUNPOWDER = cast(TicgToolParts.GUNPOWDER);

    public static final CastItemObject BARREL = cast(TicgToolParts.BARREL);
    public static final CastItemObject BOLT = cast(TicgToolParts.BOLT);
    public static final CastItemObject GUN_HANDLE = cast(TicgToolParts.GUN_HANDLE);
    public static final CastItemObject MAGAZINE = cast(TicgToolParts.MAGAZINE);
    public static final CastItemObject GUNBODY_SMALL = cast(TicgToolParts.GUNBODY_SMALL);

    private static @NotNull CastItemObject cast(ItemObject<ToolPartItem> part) {
        var cast = TIC_ITEMS.registerCast(part, ITEM_PROPS);
        FULL_LIST.add(cast);
        return cast;
    }

    static {
        miscTabDisplayItems((pParameters, pOutput) -> {
            BiConsumer<Function<CastItemObject, ItemLike>, CastItemObject> accept = (getter, cast) -> {
                pOutput.accept(getter.apply(cast));
            };
            Consumer<Function<CastItemObject, ItemLike>> addCasts = getter -> {
                FULL_LIST.forEach(
                        cast -> accept.accept(getter, cast)
                );
            };

            addCasts.accept(CastItemObject::get);
            addCasts.accept(CastItemObject::getSand);
            addCasts.accept(CastItemObject::getRedSand);
        });
    }
}
