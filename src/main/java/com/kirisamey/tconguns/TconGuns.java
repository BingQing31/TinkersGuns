package com.kirisamey.tconguns;

import com.kirisamey.tconguns.reghub.InitializeHelper;
import com.kirisamey.tconguns.reghub.RegisterHelper;
import com.mojang.logging.LogUtils;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TconGuns.MODID) @Log4j2
public class TconGuns {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "tconguns";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Lock LOCK = new ReentrantLock();

    public TconGuns(FMLJavaModLoadingContext context) {
        LOCK.lock();

        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        RegisterHelper.initRegisters(modEventBus);

        LOCK.unlock();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("TicG: TconGuns common setup");

        InitializeHelper.init();
    }
}
