package me.kall.fastspawner.mixin;

import me.kall.fastspawner.FastSpawner;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BaseSpawner.class)
public abstract class BaseSpawnerMixin {
    @Redirect(method = "clientTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void onParticle(Level instance, ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        if (FastSpawner.particle()) instance.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
