package com.kirisamey.tconguns.utils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class FriendlyBufUtils {
    public static void writeVec3(FriendlyByteBuf buf, Vec3 vec3) {
        buf.writeDouble(vec3.x);
        buf.writeDouble(vec3.y);
        buf.writeDouble(vec3.z);
    }

    public static Vec3 readVec3(FriendlyByteBuf buf) {
        var x = buf.readDouble();
        var y = buf.readDouble();
        var z = buf.readDouble();
        return new Vec3(x, y, z);
    }
}
