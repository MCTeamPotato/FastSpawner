package me.kall.fastspawner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.ThreadLocalRandom;

@Mod(FastSpawner.MOD_ID)
public final class FastSpawner {
    public static final String MOD_ID = "fastspawner";

    public static final ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec.IntValue PARTICLE_RENDERING_PERCENT;

    public FastSpawner(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, CONFIG);
    }

    public static boolean shift() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return false;
        return player.isShiftKeyDown();
    }

    public static boolean particle() {
        return ThreadLocalRandom.current().nextInt(0, 101) <= FastSpawner.PARTICLE_RENDERING_PERCENT.get();
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("FastSpawner");
        PARTICLE_RENDERING_PERCENT = builder.defineInRange("ParticleRenderingPercent(%)", 50, 0, 100);
        builder.pop();
        CONFIG = builder.build();
    }
}
