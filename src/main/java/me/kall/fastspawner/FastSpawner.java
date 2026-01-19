package me.kall.fastspawner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

@Mod(FastSpawner.MOD_ID)
public final class FastSpawner {
    public static final String MOD_ID = "fastspawner";

    public static final ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec.IntValue PARTICLE_RENDERING_PERCENT;
    public static final ForgeConfigSpec.BooleanValue SHIFT_TO_SHOW;

    public FastSpawner(@NotNull FMLJavaModLoadingContext context) {
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
        SHIFT_TO_SHOW = builder.define("ShiftToShowEntityInside", true);
        builder.pop();
        CONFIG = builder.build();
    }
}
