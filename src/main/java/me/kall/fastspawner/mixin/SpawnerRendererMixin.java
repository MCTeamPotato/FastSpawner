package me.kall.fastspawner.mixin;

import me.kall.fastspawner.FastSpawner;
import net.minecraft.client.renderer.blockentity.SpawnerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpawnerRenderer.class)
public abstract class SpawnerRendererMixin {
    @Redirect(
            method = "render(Lnet/minecraft/world/level/block/entity/SpawnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/BaseSpawner;getOrCreateDisplayEntity(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/entity/Entity;"
            )
    )
    private @Nullable Entity onGet(BaseSpawner instance, Level level, RandomSource randomSource, BlockPos blockPos) {
        if (!FastSpawner.SHIFT_TO_SHOW.get()) return null;
        return FastSpawner.shift() ? instance.getOrCreateDisplayEntity(level, randomSource, blockPos) : null;
    }
}
